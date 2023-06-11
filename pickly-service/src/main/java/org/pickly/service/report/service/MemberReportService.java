package org.pickly.service.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.report.entity.MemberReport;
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
      throw new BusinessException("이미 신고한 회원입니다.", ErrorCode.ENTITY_CONFLICT);
    }

    if (reporterId.equals(reportedId)) {
      throw new BusinessException("자신은 신고할 수 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
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
