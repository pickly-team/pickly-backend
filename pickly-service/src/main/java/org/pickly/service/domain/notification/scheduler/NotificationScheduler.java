package org.pickly.service.domain.notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.common.NotificationSender;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.service.interfaces.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

  private final NotificationService notificationService;
  private final NotificationSender notificationSender;
  private final BookmarkReadService bookmarkReadService;


  @Scheduled(cron = "0 0/30 * * * *")
  public void makeNormalNotification() {
    Map<Member, List<Bookmark>> unreadBookmarks = bookmarkReadService.findAllUnreadBookmark();
    List<Notification> notifications = notificationService.makeNormals(unreadBookmarks);
    notificationService.saveAll(notifications);
    sendNormalNotification();
  }

  private void sendNormalNotification() {
    LocalDateTime now = TimezoneHandler.getUTCnow();
    List<Notification> notifications = notificationService.getNotificationsToSend(now);
    notificationSender.sendMessage(notifications);
  }

}
