package org.pickly.service.bookmark.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final BookmarkQueryRepository bookmarkQueryRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberService memberService;

  private static final boolean USER_LIKE = true;
  private static final int LAST_ITEM = 1;

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
        USER_LIKE, null);
    return makeResponse(pageRequest.getPageSize(), memberLikes);
  }

  @Override
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long categoryId,
      final Boolean isUserRead
  ) {
    memberService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, isUserRead);
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    return makeResponse(pageRequest.getPageSize(), memberBookmarks, bookmarkCommentCntMap);
  }

  private <T> List<T> mapToDtoList(final List<Bookmark> bookmarks,
      final Function<Bookmark, T> mapper) {
    return bookmarks.stream().map(mapper).toList();
  }

  private PageResponse<BookmarkItemDTO> makeResponse(
      final int pageSize, final List<Bookmark> bookmarks) {
    int contentSize = bookmarks.size();
    boolean hasNext = PageResponse.makeHasNext(contentSize, pageSize);
    List<BookmarkItemDTO> contents = makeBookmarkRes(
        mapToDtoList(bookmarks, BookmarkItemDTO::from), contentSize);
    return new PageResponse<>(hasNext, contents);
  }

  private PageResponse<BookmarkPreviewItemDTO> makeResponse(
      final int pageSize, final List<Bookmark> bookmarks, final Map<Long, Long> commentCntMap) {
    int contentSize = bookmarks.size();
    boolean hasNext = PageResponse.makeHasNext(contentSize, pageSize);
    List<BookmarkPreviewItemDTO> contents = makeBookmarkRes(
        mapToDtoList(bookmarks,
            b -> BookmarkPreviewItemDTO.from(b, commentCntMap.get(b.getId()))),
        contentSize);
    return new PageResponse<>(hasNext, contents);
  }

  private <T> List<T> makeBookmarkRes(final List<T> contents, final int size) {
    List<T> resultList = new ArrayList<>(contents);
    resultList.remove(size - LAST_ITEM);
    Collections.reverse(resultList);
    return resultList;
  }

  @Override
  public Bookmark findById(Long id) {
    return bookmarkRepository.findOneById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 북마크입니다."));
  }

  @Override
  public void likeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.like();
  }

  @Override
  public void cancelLikeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.deleteLike();
  }

}
