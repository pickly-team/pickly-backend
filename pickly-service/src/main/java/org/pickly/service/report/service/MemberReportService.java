package org.pickly.service.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.report.entity.MemberReport;
import org.pickly.service.report.exception.ReportException;
import org.pickly.service.report.repository.MemberReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService {

  private final MemberReportRepository memberReportRepository;
  private final MemberService memberService;

  public void reportMember(Long reporterId, Long reportedId, String content) {
    if (memberReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }

    if (reporterId.equals(reportedId)) {
      throw new ReportException.CannotReportSelfException();
    }

    memberReportRepository.save(
        MemberReport.builder()
            .reporter(memberService.findById(reporterId))
            .reported(memberService.findById(reportedId))
            .content(content)
            .build()
    );
  }
}
