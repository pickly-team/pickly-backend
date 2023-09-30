package org.pickly.service.notification;

import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.NotificationStandard;

import java.time.LocalTime;

public class NotificationStandardFactory {

  public NotificationStandard testNotificationStandard(Member member) {
    return testNotificationStandard(member, true, 1, LocalTime.of(9, 0));
  }

  public NotificationStandard testNotificationStandard(
      Member member, Boolean isActive, Integer notifyStandardDay, LocalTime notifyDailyAt
  ) {
    return NotificationStandard.builder()
        .member(member).isActive(isActive)
        .notifyStandardDay(notifyStandardDay).notifyDailyAt(notifyDailyAt)
        .build();
  }
}
