package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.pickly.service.domain.report.entity.BookmarkReport;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportService {

  private final BookmarkReportRepository bookmarkReportRepository;
  private final MemberService memberService;
  private final BookmarkReadService bookmarkReadService;

  public void reportBookmark(Long reporterId, Long reportedId, String content) {
    if (bookmarkReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }

    Member reporter = memberService.findById(reporterId);
    Bookmark bookmark = bookmarkReadService.findById(reportedId);

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
