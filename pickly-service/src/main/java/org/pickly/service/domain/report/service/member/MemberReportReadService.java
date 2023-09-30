package org.pickly.service.domain.report.service.member;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.report.repository.MemberReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.report.exception.ReportException.AlreadyReportException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportReadService {

  private final MemberReportRepository memberReportRepository;

  public void checkIsAlreadyReport(Long reporterId, Long reportedId) {
    if (memberReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new AlreadyReportException();
    }
  }

}
