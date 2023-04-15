package org.pickly.service.notification;

import java.time.LocalTime;
import org.pickly.service.member.entity.Member;
import org.pickly.service.notification.entity.NotificationStandard;

public class NotificationStandardFactory {

  public NotificationStandard testNotificationStandard(Member member) {
    return testNotificationStandard(member, true, LocalTime.of(9, 0));
  }

  public NotificationStandard testNotificationStandard(
      Member member, Boolean isActive, LocalTime notifyDailyAt
  ) {
    return new NotificationStandard(member, isActive, notifyDailyAt);
  }
}
