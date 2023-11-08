package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.dto.controller.request.BookmarkCreateReq;
import org.pickly.service.domain.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.bookmark.service.BookmarkWriteService;
import org.pickly.service.domain.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.domain.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.service.CategoryReadService;
import org.pickly.service.domain.comment.service.CommentReadService;
import org.pickly.service.domain.comment.service.CommentWriteService;
import org.pickly.service.domain.friend.entity.Relationship;
import org.pickly.service.domain.friend.service.FriendReadService;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookmarkFacade {

  private final BookmarkWriteService bookmarkWriteService;
  private final BookmarkReadService bookmarkReadService;
  private final CategoryReadService categoryReadService;
  private final CommentReadService commentReadService;
  private final CommentWriteService commentWriteService;
  private final NotificationWriteService notificationWriteService;
  private final MemberReadService memberReadService;
  private final FriendReadService friendReadService;

  public Bookmark create(BookmarkCreateReq request) {
    var category = categoryReadService.findById(request.getCategoryId());
    var member = memberReadService.findById(request.getMemberId());

    BookmarkInfoDTO info = new BookmarkInfoDTO(
        request.getUrl(), request.getTitle(), request.getThumbnail()
    );
    Bookmark bookmark = Bookmark.create(
        category, member, info, request.getVisibility()
    );

    return bookmarkWriteService.create(bookmark);
  }

  @Transactional
  public void delete(Long bookmarkId) {
    var bookmark = bookmarkReadService.findById(bookmarkId);
    bookmarkWriteService.delete(bookmark);

    commentWriteService.deleteByBookmark(bookmarkId);

    notificationWriteService.deleteByBookmark(bookmarkId);
  }

  @Transactional
  public void delete(List<Long> bookmarkIds) {
    LocalDateTime now = TimezoneHandler.getUTCnow();
    bookmarkWriteService.delete(bookmarkIds, now);

    bookmarkIds.forEach(commentWriteService::deleteByBookmark);

    notificationWriteService.deleteByBookmark(bookmarkIds);
  }

  public void update(Long bookmarkId, BookmarkUpdateReqDTO request) {
    var bookmark = bookmarkReadService.findById(bookmarkId);
    var category = categoryReadService.findById(request.getCategoryId());

    bookmarkWriteService.update(bookmark, category, request);
  }

  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long loginId,
      final Long categoryId, final Boolean readByUser
  ) {
    memberReadService.existsById(memberId);
    memberReadService.existsById(loginId);

    Relationship relationship = checkRelationShip(loginId, memberId);
    List<Bookmark> bookmarks = bookmarkReadService.findMemberBookmarks(
        pageRequest, memberId, relationship, categoryId, readByUser
    );

    Map<Long, Long> bookmarkCommentCntMap = commentReadService.getBookmarkCommentCnt(memberId);

    return bookmarkReadService.makeResponse(
        pageRequest.getPageSize(), bookmarks, bookmarkCommentCntMap
    );
  }

  public PageResponse<BookmarkPreviewItemDTO> searchBookmarks(
      final PageRequest pageRequest, final Long memberId, final String keyword
  ) {
    memberReadService.existsById(memberId);

    List<Bookmark> bookmarks = bookmarkReadService.searchBookmarks(pageRequest, memberId, keyword);
    Map<Long, Long> bookmarkCommentCntMap = commentReadService.getBookmarkCommentCntByKeyword(memberId, keyword);

    return bookmarkReadService.makeResponse(
        pageRequest.getPageSize(), bookmarks, bookmarkCommentCntMap
    );
  }

  private Relationship checkRelationShip(Long loginId, Long memberId) {
    if (memberId.equals(loginId)) {
      return Relationship.ME;
    }

    boolean isFriend = friendReadService.checkUsersAreFriend(loginId, memberId);
    return (isFriend) ? Relationship.FRIEND : Relationship.OTHERS;
  }

  public BookmarkReadStatus getBookmarkReadStatus(final long memberId) {
    memberReadService.existsById(memberId);
    var totalBookmarkCount = bookmarkReadService.countByMemberId(memberId);
    var readBookmarkCount = bookmarkReadService.countReadBookmarksByMemberId(memberId);
    return new BookmarkReadStatus(totalBookmarkCount, readBookmarkCount);
  }

  public BookmarkReadStatus getBookmarkReadStatusByCategory(
      final long memberId, final long categoryId
  ) {
    memberReadService.existsById(memberId);
    categoryReadService.existsById(categoryId);
    var totalBookmarkCount = bookmarkReadService.countByCategoryId(categoryId);
    var readBookmarkCount = bookmarkReadService.countReadBookmarksByCategoryId(categoryId);
    return new BookmarkReadStatus(totalBookmarkCount, readBookmarkCount);
  }

  public Map<Category, BookmarkReadStatus> getCategoryReadStatus(final long memberId) {
    memberReadService.existsById(memberId);
    return bookmarkReadService.getCategoryReadStatus(memberId);
  }

}
