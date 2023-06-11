package org.pickly.service.notification;

import java.time.LocalTime;
import org.pickly.service.member.entity.Member;
import org.pickly.service.notification.entity.NotificationStandard;

public class NotificationStandardFactory {

  public NotificationStandard testNotificationStandard(Member member) {
    return testNotificationStandard(member, true, 1, LocalTime.of(9, 0));
  }

  public NotificationStandard testNotificationStandard(
      Member member, Boolean isActive, Integer notifyStandardDay, LocalTime notifyDailyAt
  ) {
    return new NotificationStandard(member, isActive, notifyStandardDay, notifyDailyAt);
  }
}
