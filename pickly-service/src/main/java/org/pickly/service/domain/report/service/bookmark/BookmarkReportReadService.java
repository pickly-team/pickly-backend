package org.pickly.service.domain.report.service.bookmark;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportReadService {

  private final BookmarkReportRepository bookmarkReportRepository;

  public void checkIsValidReport(Long reporterId, Long reportedId) {
    if (bookmarkReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }
  }

}
