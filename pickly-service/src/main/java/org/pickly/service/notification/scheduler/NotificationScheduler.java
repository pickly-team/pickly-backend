package org.pickly.service.notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

  private final NotificationService notificationService;
  private final BookmarkService bookmarkService;

  private static final String KST = "Asia/Seoul";

  @Scheduled(cron = "0 0,30 * * * *")
  public void makeNormalNotification() {

    Map<Member, List<Bookmark>> unreadBookmarks = bookmarkService.findAllUnreadBookmark();
    List<Notification> notifications = notificationService.makeNormals(unreadBookmarks);
    notificationService.saveAll(notifications);

  }

  @Scheduled(cron = "0 0,30 * * * *")
  public void sendNormalNotification() {

    LocalDateTime now = LocalDateTime.now(ZoneId.of(KST));
    List<Notification> notifications = notificationService.getNotificationsToSend(now);

    // FCM을 이용해 송신

  }

}
