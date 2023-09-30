package org.pickly.service.domain.report.service.member;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.entity.MemberReport;
import org.pickly.service.domain.report.repository.MemberReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.report.exception.ReportException.CannotReportSelfException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReportWriteService {

  private final MemberReportRepository memberReportRepository;

  public void save(Member fromMember, Member toMember, String content) {
    isValidReport(fromMember.getId(), toMember.getId());
    memberReportRepository.save(
        MemberReport.builder()
            .reporter(fromMember)
            .reported(toMember)
            .content(content)
            .build()
    );
  }

  private void isValidReport(Long fromMemberId, Long toMemberId) {
    if (fromMemberId.equals(toMemberId)) {
      throw new CannotReportSelfException();
    }
  }

}
