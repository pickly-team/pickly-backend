package org.pickly.service.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.member.service.MemberWriteService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SleeperChecker {

  private final MemberReadService memberReadService;
  private final MemberWriteService memberWriteService;

  @Scheduled(cron = "0 0 0 * * *")
  public void checkSleepMember() {
    final LocalDate current = LocalDate.now();
    List<Member> sleepers = memberReadService.findLongTermInactive(current);
    memberWriteService.convertToSleeper(sleepers);
  }

}
