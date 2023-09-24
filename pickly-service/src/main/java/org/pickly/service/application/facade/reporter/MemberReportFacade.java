package org.pickly.service.application.facade.reporter;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.report.service.member.MemberReportReadService;
import org.pickly.service.domain.report.service.member.MemberReportWriteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReportFacade {

  private final MemberReportWriteService memberReportWriteService;
  private final MemberReportReadService memberReportReadService;
  private final MemberReadService memberReadService;

  public void report(Long fromMemberId, Long toMemberId, String content) {
    memberReportReadService.checkIsAlreadyReport(fromMemberId, toMemberId);

    var fromMember = memberReadService.findById(fromMemberId);
    var toMember = memberReadService.findById(toMemberId);
    memberReportWriteService.save(fromMember, toMember, content);
  }

}
