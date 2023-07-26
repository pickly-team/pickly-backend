package org.pickly.service.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.report.entity.BookmarkReport;
import org.pickly.service.report.exception.ReportException;
import org.pickly.service.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportService {

  private final BookmarkReportRepository bookmarkReportRepository;
  private final MemberService memberService;
  private final BookmarkService bookmarkService;

  public void reportBookmark(Long reporterId, Long reportedId, String content) {
    if (bookmarkReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }

    Member reporter = memberService.findById(reporterId);
    Bookmark bookmark = bookmarkService.findById(reportedId);

    if (reporter.equals(bookmark.getMember())) {
      throw new ReportException.CannotReportSelfException();
    }

    bookmarkReportRepository.save(
        BookmarkReport.builder()
            .reporter(reporter)
            .reported(bookmark)
            .content(content)
            .build()
    );
  }
}
