package org.pickly.service.domain.bookmark.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.domain.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.domain.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.domain.bookmark.exception.BookmarkException;
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.domain.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.domain.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.domain.friend.entity.Relationship;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.exception.MemberException;
import org.pickly.service.domain.member.repository.interfaces.MemberRepository;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkReadService {

  private static final boolean USER_LIKE = true;
  private static final int LAST_ITEM = 1;
  private static final String CONTENT_ATTR = "content";

  private final MemberRepository memberRepository;
  private final BookmarkRepository bookmarkRepository;
  private final BookmarkQueryRepository bookmarkQueryRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberReadService memberReadService;

  public Long countMemberLikes(final Long memberId) {
    memberReadService.existsById(memberId);
    return bookmarkQueryRepository.count(memberId, USER_LIKE);
  }

  public PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(final PageRequest pageRequest,
                                                               final Long memberId) {
    memberReadService.existsById(memberId);
    List<Bookmark> memberLikes = bookmarkQueryRepository.findBookmarks(pageRequest, memberId, null,
        USER_LIKE, null, null);
    return makeResponse(pageRequest.getPageSize(), memberLikes);
  }

  public PageResponse<BookmarkPreviewItemDTO> findMyBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long categoryId,
      final Boolean readByUser, final Visibility visibility
  ) {
    memberReadService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, readByUser, Collections.singletonList(visibility));
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    return makeResponse(pageRequest.getPageSize(), memberBookmarks, bookmarkCommentCntMap);
  }

  public List<Bookmark> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId,
      final Relationship relationship, final Long categoryId, final Boolean readByUser
  ) {
    List<Visibility> visibilities = getVisibility(relationship);
    return bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, readByUser, visibilities);
  }

  private List<Visibility> getVisibility(final Relationship relationship) {
    return switch (relationship) {
      case FRIEND -> List.of(Visibility.SCOPE_PUBLIC, Visibility.SCOPE_FRIEND);
      case ME -> List.of(Visibility.SCOPE_PUBLIC, Visibility.SCOPE_FRIEND, Visibility.SCOPE_PRIVATE);
      default -> List.of(Visibility.SCOPE_PUBLIC);
    };
  }

  private <T> List<T> mapToDtoList(
      final List<Bookmark> bookmarks, final Function<Bookmark, T> mapper
  ) {
    return bookmarks.stream().map(mapper).toList();
  }

  private PageResponse<BookmarkItemDTO> makeResponse(final int pageSize, List<Bookmark> bookmarks) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkItemDTO> contents = mapToDtoList(bookmarks, BookmarkItemDTO::from);
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  public PageResponse<BookmarkPreviewItemDTO> makeResponse(
      final int pageSize, List<Bookmark> bookmarks, final Map<Long, Long> commentCntMap) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkPreviewItemDTO> contents = mapToDtoList(bookmarks,
        b -> BookmarkPreviewItemDTO.from(b, commentCntMap.get(b.getId())));
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private List<Bookmark> removeElement(
      final List<Bookmark> bookmarkList, final int size, final int pageSize
  ) {
    if (size - LAST_ITEM >= pageSize) {
      List<Bookmark> resultList = new ArrayList<>(bookmarkList);
      resultList.remove(size - LAST_ITEM);
      return resultList;
    }
    return bookmarkList;
  }

  public Bookmark findByIdAndRead(Long id, Long memberId) {
    bookmarkRepository.readByUser(id, memberId);
    return bookmarkRepository.findOneById(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  public Bookmark findById(Long id) {
    return bookmarkRepository.findOneById(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  public Bookmark findByIdWithCategory(Long id) {
    return bookmarkRepository.findByIdWithCategory(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  public String getTitleFromUrl(Long memberId, String url) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
    BookmarkInfoDTO info = scrapOgTagInfo(url, member);
    return info.getTitle();
  }

  public BookmarkInfoDTO scrapOgTagInfo(final String url, final Member member) {
    BookmarkInfoDTO result = new BookmarkInfoDTO(url);
    try {
      Document doc = Jsoup.connect(url).get();

      String title = doc.select("meta[property=og:title]").attr(CONTENT_ATTR);
      String previewImageUrl = doc.select("meta[property=og:image]").attr(CONTENT_ATTR);

      result.updateTitleAndImage(title, previewImageUrl, member.getTimezone());
      return result;
    } catch (IOException e) {
      throw new BookmarkException.ForbiddenBookmarkException();
    }
  }

  public PageResponse<BookmarkItemDTO> findBookmarkByCategoryId(final PageRequest pageRequest,
                                                                final Long categoryId) {
    List<Bookmark> bookmarks = bookmarkQueryRepository.findBookmarkByCategoryId(pageRequest,
        categoryId);
    return makeResponse(pageRequest.getPageSize(), bookmarks);
  }

  public Map<Member, List<Bookmark>> findAllUnreadBookmark() {
    List<Bookmark> unreadBookmarks = bookmarkQueryRepository.findAllUnreadBookmark();
    return unreadBookmarks.stream().collect(
        Collectors.groupingBy(
            Bookmark::getMember, LinkedHashMap::new, Collectors.toList()
        )
    );
  }

  public Long countByMemberId(Long memberId) {
    return bookmarkRepository.countByMemberIdAndDeletedAtNull(memberId);
  }

}