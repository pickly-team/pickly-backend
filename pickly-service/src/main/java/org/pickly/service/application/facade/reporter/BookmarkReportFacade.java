package org.pickly.service.application.facade.reporter;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.report.service.bookmark.BookmarkReportReadService;
import org.pickly.service.domain.report.service.bookmark.BookmarkReportWriteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkReportFacade {

  private final BookmarkReportWriteService bookmarkReportWriteService;
  private final BookmarkReportReadService bookmarkReportReadService;
  private final MemberReadService memberReadService;
  private final BookmarkReadService bookmarkReadService;

  public void report(Long fromMemberId, Long toBookmarkId, String content) {
    bookmarkReportReadService.checkIsValidReport(fromMemberId, toBookmarkId);

    var member = memberReadService.findById(fromMemberId);
    var bookmark = bookmarkReadService.findById(toBookmarkId);

    bookmarkReportWriteService.save(member, bookmark, content);
  }

}
