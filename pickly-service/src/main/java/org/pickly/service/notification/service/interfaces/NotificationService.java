package org.pickly.service.notification.service.interfaces;

import java.util.List;
import org.pickly.service.notification.service.dto.NotificationDTO;

public interface NotificationService {

  List<NotificationDTO> findMemberNotifications(Long memberId);

  void readNotification(Long notificationId);

  void deleteNotification(Long notificationId);

}
