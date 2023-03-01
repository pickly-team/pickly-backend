package org.pickly.service.bookmark.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.dto.controller.BaseBookmarkRes;
import org.pickly.service.bookmark.dto.controller.MemberBookmarkRes;
import org.pickly.service.bookmark.dto.controller.MemberLikeBookmarkRes;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
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

  @Override
  public Long countMemberLikes(final Long memberId) {
    memberService.existsById(memberId);
    return bookmarkQueryRepository.count(memberId, USER_LIKE);
  }

  @Override
  public MemberLikeBookmarkRes findMemberLikeBookmarks(
      final Long cursorId, final Long memberId, final Integer pageSize) {
    memberService.existsById(memberId);
    List<Bookmark> memberLikes = bookmarkQueryRepository.findBookmarks(cursorId, memberId, null,
        USER_LIKE, null, pageSize);
    List<BookmarkItemDTO> dtos = memberLikes.stream().map(BookmarkItemDTO::from).toList();
//    return makeBookmarkRes(dtos, pageSize, MemberLikeBookmarkRes::from);
//    return makeMemberLikeBookmarkRes(dtos, pageSize);
    return makeBookmarkRes(dtos, pageSize, MemberLikeBookmarkRes::from);
  }

  @Override
  public MemberBookmarkRes findMemberBookmarks(
      final Long cursorId, final Long memberId, final Long categoryId, final Boolean isUserRead,
      final Integer pageSize
  ) {
    memberService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(cursorId, memberId,
        categoryId,
        USER_LIKE, isUserRead, pageSize);
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    List<BookmarkPreviewItemDTO> dtos = memberBookmarks.stream().map(
        b -> BookmarkPreviewItemDTO.from(b, bookmarkCommentCntMap.get(b.getId()))
    ).toList();
//    return makeBookmarkRes(dtos, pageSize, MemberBookmarkRes::from);
//    return makeBookmarkRes(dtos, pageSize);
    return makeBookmarkRes(dtos, pageSize, MemberBookmarkRes::from);
  }

//  private MemberLikeBookmarkRes makeMemberLikeBookmarkRes(final List<BookmarkItemDTO> contents,
//      final Integer pageSize) {
//    ArrayList<BookmarkItemDTO> arrayList = new ArrayList<>(contents);
//    int contentSize = arrayList.size();
//    boolean hasNext = contentSize > pageSize;
//    if (!hasNext) {
//      arrayList.remove(contentSize - 1);
//    }
//    return MemberLikeBookmarkRes.from(arrayList, hasNext);
//  }
//
//  private MemberBookmarkRes makeBookmarkRes(final List<BookmarkPreviewItemDTO> contents,
//      final Integer pageSize) {
//    ArrayList<BookmarkPreviewItemDTO> arrayList = new ArrayList<>(contents);
//    int contentSize = arrayList.size();
//    boolean hasNext = contentSize > pageSize;
//    if (!hasNext) {
//      arrayList.remove(contentSize - 1);
//    }
//    return MemberBookmarkRes.from(arrayList, hasNext);
//  }


  private <T extends BaseBookmarkRes<E>, E> T makeBookmarkRes(final List<E> contents, final Integer pageSize, BiFunction<List<E>, Boolean, T> constructor) {
    ArrayList<E> arrayList = new ArrayList<>(contents);
    int contentSize = arrayList.size();
    boolean hasNext = contentSize > pageSize;
    if (!hasNext) {
      arrayList.remove(contentSize - 1);
    }
    return constructor.apply(arrayList, hasNext);
  }

}
