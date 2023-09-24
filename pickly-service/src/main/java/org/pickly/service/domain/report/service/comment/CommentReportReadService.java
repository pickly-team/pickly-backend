package org.pickly.service.domain.report.service.comment;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.CommentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportReadService {

  private final CommentReportRepository commentReportRepository;

  public void checkIsAlreadyReport(Long reporterId, Long reportedId) {
    if (commentReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }
  }

}
