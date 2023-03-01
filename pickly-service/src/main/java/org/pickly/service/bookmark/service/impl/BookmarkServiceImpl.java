package org.pickly.service.bookmark.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkQueryRepository bookmarkQueryRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberService memberService;

  private static final boolean USER_LIKE = true;
  private static final int UNUSED_ITEM = 1;

  @Override
  public Long countMemberLikes(final Long memberId) {
    memberService.existsById(memberId);
    return bookmarkQueryRepository.count(memberId, USER_LIKE);
  }

  @Override
  public PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(final PageRequest pageRequest, final Long memberId) {
    memberService.existsById(memberId);
    List<Bookmark> memberLikes = bookmarkQueryRepository.findBookmarks(pageRequest, memberId, null,
        USER_LIKE, null);
    List<BookmarkItemDTO> dtos = memberLikes.stream().map(BookmarkItemDTO::from).toList();
    int contentSize = dtos.size();
    boolean hasNext = makeHasNext(contentSize, pageRequest.getPageSize());
    List<BookmarkItemDTO> contents = makeBookmarkRes(dtos, contentSize);
    return new PageResponse<>(hasNext, contents);
  }

  @Override
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long categoryId, final Boolean isUserRead
  ) {
    memberService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, USER_LIKE, isUserRead);
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    List<BookmarkPreviewItemDTO> dtos = memberBookmarks.stream().map(
        b -> BookmarkPreviewItemDTO.from(b, bookmarkCommentCntMap.get(b.getId()))
    ).toList();
    int contentSize = dtos.size();
    boolean hasNext = makeHasNext(contentSize, pageRequest.getPageSize());
    List<BookmarkPreviewItemDTO> contents = makeBookmarkRes(dtos, contentSize);
    return new PageResponse<>(hasNext, contents);
  }


  private boolean makeHasNext(final int contentSize, final int pageSize) {
    return contentSize > pageSize;
  }

  private <T> List<T> makeBookmarkRes(final List<T> contents, final int contentSize) {
    List<T> resultList = new ArrayList<>(contents);
    resultList.remove(contentSize - UNUSED_ITEM);
    return resultList;
  }

}
