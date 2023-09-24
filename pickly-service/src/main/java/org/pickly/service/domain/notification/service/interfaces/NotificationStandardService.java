package org.pickly.service.domain.notification.service.interfaces;

import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.service.dto.NotificationStandardDTO;
import org.pickly.service.domain.notification.service.dto.NotifyStandardDayUpdateDTO;

public interface NotificationStandardService {

  NotificationStandard findByMemberId(Long memberId);

  Integer findMyNotifyStandardDay(Long memberId);

  void createNotificationStandard(Long memberId, NotificationStandardDTO dto);

  void updateNotificationStandard(Long memberId, NotificationStandardDTO dto);

  void updateMyNotifyStandardDay(Long memberId, NotifyStandardDayUpdateDTO dto);

}
