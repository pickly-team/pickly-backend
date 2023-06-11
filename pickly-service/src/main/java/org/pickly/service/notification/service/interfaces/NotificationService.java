package org.pickly.service.notification.service.interfaces;

import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.member.entity.Member;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.service.dto.NotificationDTO;

import java.util.List;
import java.util.Map;

public interface NotificationService {

  List<NotificationDTO> findMemberNotifications(Long memberId);

  List<Notification> makeNormals(Map<Member, List<Bookmark>> unreadBookmarks);

  void readNotification(Long notificationId);

  void deleteNotification(Long notificationId);

  void saveAll(List<Notification> notifications);

}
