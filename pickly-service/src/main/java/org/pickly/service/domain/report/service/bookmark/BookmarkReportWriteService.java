package org.pickly.service.domain.report.service.bookmark;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.entity.BookmarkReport;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportWriteService {

  private final BookmarkReportRepository bookmarkReportRepository;

  public void save(Member reporter, Bookmark bookmark, String content) {
    checkValidReporter(reporter, bookmark.getMember());
    bookmarkReportRepository.save(
        BookmarkReport.builder()
            .reporter(reporter)
            .reported(bookmark)
            .content(content)
            .build()
    );
  }

  private void checkValidReporter(Member reporter, Member author) {
    if (reporter.equals(author)) {
      throw new ReportException.CannotReportSelfException();
    }
  }

}
