package org.pickly.service.bookmark.service.impl;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pickly.service.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.exception.BookmarkException;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.exception.CategoryException;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.exception.MemberException;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private static final boolean USER_LIKE = true;
  private static final int LAST_ITEM = 1;
  private static final String CONTENT_ATTR = "content";


  private final BookmarkRepository bookmarkRepository;
  private final BookmarkQueryRepository bookmarkQueryRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberService memberService;

  private final CategoryRepository categoryRepository;

  private final MemberRepository memberRepository;

  @Override
  public Long countMemberLikes(final Long memberId) {
    memberService.existsById(memberId);
    return bookmarkQueryRepository.count(memberId, USER_LIKE);
  }

  @Override
  public PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(final PageRequest pageRequest,
                                                               final Long memberId) {
    memberService.existsById(memberId);
    List<Bookmark> memberLikes = bookmarkQueryRepository.findBookmarks(pageRequest, memberId, null,
        USER_LIKE, null, null);
    return makeResponse(pageRequest.getPageSize(), memberLikes);
  }

  @Override
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long categoryId,
      final Boolean readByUser, final Visibility visibility
  ) {
    memberService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, readByUser, visibility);
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    return makeResponse(pageRequest.getPageSize(), memberBookmarks, bookmarkCommentCntMap);
  }


  private <T> List<T> mapToDtoList(final List<Bookmark> bookmarks,
                                   final Function<Bookmark, T> mapper) {
    return bookmarks.stream().map(mapper).toList();
  }

  private PageResponse<BookmarkItemDTO> makeResponse(final int pageSize, List<Bookmark> bookmarks) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkItemDTO> contents = mapToDtoList(bookmarks, BookmarkItemDTO::from);
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private PageResponse<BookmarkPreviewItemDTO> makeResponse(
      final int pageSize, List<Bookmark> bookmarks, final Map<Long, Long> commentCntMap) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkPreviewItemDTO> contents = mapToDtoList(bookmarks,
        b -> BookmarkPreviewItemDTO.from(b, commentCntMap.get(b.getId())));
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private List<Bookmark> removeElement(final List<Bookmark> bookmarkList, final int size,
                                       final int pageSize) {
    if (size - LAST_ITEM >= pageSize) {
      List<Bookmark> resultList = new ArrayList<>(bookmarkList);
      resultList.remove(size - LAST_ITEM);
      return resultList;
    }
    return bookmarkList;
  }

  @Override
  public Bookmark findByIdAndRead(Long id, Long memberId) {
    bookmarkRepository.readByUser(id, memberId);
    return bookmarkRepository.findOneById(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  @Override
  public Bookmark findById(Long id) {
    return bookmarkRepository.findOneById(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  @Override
  public Bookmark findByIdWithCategory(Long id) {
    return bookmarkRepository.findByIdWithCategory(id)
        .orElseThrow(BookmarkException.BookmarkNotFoundException::new);
  }

  @Override
  public String getTitleFromUrl(String url) {
    BookmarkInfoDTO info = scrapOgTagInfo(url);
    return info.getTitle();
  }


  @Override
  @Transactional
  public void likeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.like();
  }

  @Override
  @Transactional
  public void cancelLikeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.deleteLike();
  }


  @Override
  @Transactional
  public BookmarkDeleteResDTO deleteBookmark(Long bookmarkId) {
    BookmarkDeleteResDTO bookmarkDeleteReqDTO = new BookmarkDeleteResDTO();
    Bookmark bookmark = findById(bookmarkId);
    bookmark.delete();
    bookmarkDeleteReqDTO.setIsDeleted();
    return bookmarkDeleteReqDTO;
  }


  @Override
  @Transactional
  public BookmarkListDeleteResDTO deleteBookmarks(List<Long> bookmarkIds) {
    BookmarkListDeleteResDTO bookmarkListDeleteResDTO = new BookmarkListDeleteResDTO();
    LocalDateTime now = TimezoneHandler.getUTCnow();
    bookmarkRepository.deleteBookmarksByIds(bookmarkIds, now);
    bookmarkListDeleteResDTO.setIsDeleted();
    return bookmarkListDeleteResDTO;
  }

  @Override
  @Transactional
  public Bookmark create(BookmarkCreateReq dto) {

    Category category = categoryRepository.findById(dto.getCategoryId())
        .orElseThrow(CategoryException.CategoryNotFoundException::new);
    Member member = memberRepository.findById(dto.getMemberId())
        .orElseThrow(MemberException.MemberNotFoundException::new);

    BookmarkInfoDTO info = scrapOgTagInfo(dto.getUrl());
    Bookmark entity = Bookmark.create(category, member, dto.getTitle(), info, dto.getVisibility());

    return bookmarkRepository.save(entity);
  }

  private BookmarkInfoDTO scrapOgTagInfo(final String url) {
    BookmarkInfoDTO result = new BookmarkInfoDTO(url);
    try {
      Document doc = Jsoup.connect(url).get();

      String title = doc.select("meta[property=og:title]").attr(CONTENT_ATTR);
      String previewImageUrl = doc.select("meta[property=og:image]").attr(CONTENT_ATTR);

      result.updateTitleAndImage(title, previewImageUrl);
      return result;
    } catch (IOException e) {
      return result;
    }
  }


  @Override
  public PageResponse<BookmarkItemDTO> findBookmarkByCategoryId(final PageRequest pageRequest,
                                                                final Long categoryId) {
    List<Bookmark> bookmarks = bookmarkQueryRepository.findBookmarkByCategoryId(pageRequest,
        categoryId);
    return makeResponse(pageRequest.getPageSize(), bookmarks);
  }

  @Transactional
  @Override
  public void updateBookmark(Long bookmarkId, BookmarkUpdateReqDTO request) {
    Bookmark bookmark = findById(bookmarkId);

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(CategoryException.CategoryNotFoundException::new);

    bookmark.updateBookmark(
        category,
        request.getTitle(),
        request.getReadByUser(),
        request.getVisibility()
    );
  }

  @Override
  public Map<Member, List<Bookmark>> findAllUnreadBookmark() {
    List<Bookmark> unreadBookmarks = bookmarkRepository.findAllUnreadBookmark();
    return unreadBookmarks.stream().collect(
        Collectors.groupingBy(
            Bookmark::getMember, LinkedHashMap::new, Collectors.toList()
        )
    );
  }

  /**
   * @param categoryId
   * @description : 카테고리 삭제시 해당 카테고리에 속한 북마크 삭제
   */
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void findBookmarkByCategoryIdAndDelete(final Long categoryId) {
    PageRequest pageRequest = new PageRequest(null, 15);
    LocalDateTime now = TimezoneHandler.getNowByZone();
    List<Bookmark> bookmarks = bookmarkQueryRepository.findBookmarkByCategoryId(pageRequest,
        categoryId);
    List<Long> bookmarkIds = bookmarks.stream().map(Bookmark::getId).toList();
    bookmarkRepository.deleteBookmarksByIds(bookmarkIds, now);
  }
}
