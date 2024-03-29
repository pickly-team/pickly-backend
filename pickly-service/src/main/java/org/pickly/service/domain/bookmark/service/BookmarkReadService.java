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
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.domain.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.domain.bookmark.vo.BookmarkCrawlInfo;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.friend.entity.Relationship;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.repository.interfaces.MemberRepository;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.pickly.service.domain.bookmark.exception.BookmarkException.BookmarkNotFoundException;
import static org.pickly.service.domain.bookmark.exception.BookmarkException.ForbiddenBookmarkException;

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

  public List<Bookmark> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId,
      final Relationship relationship, final Long categoryId, final Boolean readByUser
  ) {
    List<Visibility> visibilities = relationship.getVisibility();
    return bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, readByUser, visibilities);
  }

  public List<Bookmark> searchBookmarks(
      final PageRequest pageRequest, final Long memberId, final String keyword
  ) {
    return bookmarkQueryRepository.searchBookmarks(pageRequest, memberId, keyword);
  }

  private <T> List<T> mapToDtoList(
      final List<Bookmark> bookmarks, final Function<Bookmark, T> mapper
  ) {
    return bookmarks.stream().map(mapper).toList();
  }

  public PageResponse<BookmarkItemDTO> makeResponse(final int pageSize, List<Bookmark> bookmarks) {
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
        .orElseThrow(BookmarkNotFoundException::new);
  }

  public Bookmark findById(Long id) {
    return bookmarkRepository.findOneById(id)
        .orElseThrow(BookmarkNotFoundException::new);
  }

  public Bookmark findByIdWithCategory(Long id) {
    return bookmarkRepository.findByIdWithCategory(id)
        .orElseThrow(BookmarkNotFoundException::new);
  }

  public BookmarkCrawlInfo getInfoFromUrl(final String url) {
    BookmarkInfoDTO info = scrapOgTagInfo(url);
    return new BookmarkCrawlInfo(url, info.getTitle(), info.getPreviewImageUrl());
  }

  public BookmarkInfoDTO scrapOgTagInfo(final String url) {
    try {
      Document doc = Jsoup.connect(url).get();

      String title = doc.select("meta[property=og:title]").attr(CONTENT_ATTR);
      String previewImageUrl = doc.select("meta[property=og:image]").attr(CONTENT_ATTR);

      return new BookmarkInfoDTO(url, title, previewImageUrl);
    } catch (IOException e) {
      throw new ForbiddenBookmarkException();
    }
  }

  public PageResponse<BookmarkItemDTO> findBookmarkByCategoryId(
      final PageRequest pageRequest, final Long categoryId
  ) {
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

  public Long countReadBookmarksByMemberId(Long memberId) {
    return bookmarkRepository.countReadBookmarksByMemberId(memberId);
  }

  public Long countByCategoryId(final Long categoryId) {
    return bookmarkRepository.countByCategoryIdAndDeletedAtIsNull(categoryId);
  }

  public Long countReadBookmarksByCategoryId(final Long categoryId) {
    return bookmarkRepository.countReadBookmarksByCategoryId(categoryId);
  }

  public Map<Category, BookmarkReadStatus> getCategoryReadStatus(final Long memberId) {
    return bookmarkQueryRepository.findCategoryReadStatus(memberId);
  }

}
