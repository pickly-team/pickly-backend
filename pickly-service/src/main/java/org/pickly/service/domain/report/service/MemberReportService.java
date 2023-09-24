package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.interfaces.MemberReadService;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.MemberReportRepository;
import org.pickly.service.domain.report.entity.MemberReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

  private final MemberReportRepository memberReportRepository;
  private final MemberReadService memberReadService;

  public void reportMember(Long reporterId, Long reportedId, String content) {
    if (memberReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }

    if (reporterId.equals(reportedId)) {
      throw new ReportException.CannotReportSelfException();
    }

    memberReportRepository.save(
        MemberReport.builder()
            .reporter(memberReadService.findById(reporterId))
            .reported(memberReadService.findById(reportedId))
            .content(content)
            .build()
    );
  }
}
