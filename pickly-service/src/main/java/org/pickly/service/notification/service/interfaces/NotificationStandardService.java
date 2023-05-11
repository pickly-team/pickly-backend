package org.pickly.service.notification.service.interfaces;

import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.service.dto.NotificationStandardDTO;

public interface NotificationStandardService {

  NotificationStandard findByMemberId(Long memberId);

  Integer findMyNotifyStandardDay(Long memberId);

  void createNotificationStandard(Long memberId, NotificationStandardDTO dto);

  void updateNotificationStandard(Long memberId, NotificationStandardDTO dto);
}
