package org.pickly.service.domain.notification.service.interfaces;

import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.service.dto.NotificationDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface NotificationService {

  List<NotificationDTO> findMemberNotifications(Long memberId);

  List<Notification> makeNormals(Map<Member, List<Bookmark>> unreadBookmarks);

  List<Notification> getNotificationsToSend(LocalDateTime now);

  void readNotification(Long notificationId);

  void deleteNotification(Long notificationId);

  void saveAll(List<Notification> notifications);

  void updateAllToSend(List<Notification> notifications);

}
