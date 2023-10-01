package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.common.ReportType;
import org.pickly.service.domain.report.entity.BookmarkReport;
import org.pickly.service.domain.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.report.exception.ReportException.FailToReportException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportService extends ReportService<BookmarkReport> {

  private final BookmarkReportRepository bookmarkReportRepository;

  @Override
  public BookmarkReport create(Member reporter, Object reported, String content) {
    Bookmark target = getTarget(reported);
    checkValidReport(reporter.getId(), target.getMember().getId());
    return bookmarkReportRepository.save(
        BookmarkReport.builder()
            .reporter(reporter)
            .reported(target)
            .content(content)
            .build()
    );
  }

  private Bookmark getTarget(Object reported) {
    if (!(reported instanceof Bookmark)) {
      throw new FailToReportException();
    }
    return (Bookmark) reported;
  }

  @Override
  public ReportType getReportType() {
    return ReportType.BOOKMARK;
  }

}
