package org.pickly.service.notification;

import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationFactory {

  public Notification testNotification(Long memberId, Long bookmarkId) {
    return Notification.builder()
        .memberId(memberId)
        .bookmarkId(bookmarkId)
        .title("알림이에요!")
        .content("3일 전에 추가한 000 북마크를 읽어보세요!")
        .isChecked(false)
        .isSend(true)
        .sendDateTime(LocalDateTime.now())
        .notificationType(NotificationType.NORMAL)
        .build();
  }

}
