package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.bookmark.service.BookmarkWriteService;
import org.pickly.service.domain.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.domain.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.domain.category.service.CategoryReadService;
import org.pickly.service.domain.comment.service.CommentWriteService;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkFacade {

  private final BookmarkWriteService bookmarkWriteService;
  private final BookmarkReadService bookmarkReadService;
  private final CategoryReadService categoryReadService;
  private final CommentWriteService commentWriteService;
  private final NotificationWriteService notificationWriteService;
  private final MemberReadService memberReadService;

  public Bookmark create(BookmarkCreateReq request) {
    var category = categoryReadService.findById(request.getCategoryId());
    var member = memberReadService.findById(request.getMemberId());

    BookmarkInfoDTO info = bookmarkReadService.scrapOgTagInfo(request.getUrl(), member);
    Bookmark bookmark = Bookmark.create(category, member, request.getTitle(), info, request.getVisibility());

    return bookmarkWriteService.create(bookmark);
  }

  public void delete(Long bookmarkId) {
    var bookmark = bookmarkReadService.findById(bookmarkId);
    bookmarkWriteService.delete(bookmark);

    commentWriteService.deleteByBookmark(bookmarkId);

    notificationWriteService.deleteByBookmark(bookmarkId);
  }

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

}
