package org.pickly.service.domain.report.service.bookmark;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.report.exception.ReportException.AlreadyReportException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportReadService {

  private final BookmarkReportRepository bookmarkReportRepository;

  public void checkIsValidReport(Long reporterId, Long reportedId) {
    if (bookmarkReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new AlreadyReportException();
    }
  }

}
