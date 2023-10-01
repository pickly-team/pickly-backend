package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.common.ReportType;
import org.pickly.service.domain.report.entity.MemberReport;
import org.pickly.service.domain.report.repository.MemberReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.report.exception.ReportException.FailToReportException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportService extends ReportService<MemberReport> {

  private final MemberReportRepository memberReportRepository;

  @Override
  public MemberReport create(Member reporter, Object reported, String content) {
    Member target = getTarget(reported);
    checkValidReport(reporter.getId(), target.getId());
    return memberReportRepository.save(
        MemberReport.builder()
            .reporter(reporter)
            .reported(target)
            .content(content)
            .build()
    );
  }

  private Member getTarget(Object reported) {
    if (!(reported instanceof Member)) {
      throw new FailToReportException();
    }
    return (Member) reported;
  }

  @Override
  public ReportType getReportType() {
    return ReportType.MEMBER;
  }

}
