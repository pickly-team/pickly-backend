package org.pickly.service.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.entity.NotificationTemplate;
import org.pickly.service.domain.notification.enums.NotificationType;
import org.pickly.service.domain.notification.repository.interfaces.NotificationRepository;
import org.pickly.service.domain.notification.service.standard.NotificationStandardReadService;
import org.pickly.service.domain.notification.service.template.NotificationTemplateReadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.pickly.service.domain.notification.exception.NotificationException.NotificationNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationReadService {

  private final NotificationRepository notificationRepository;
  private final NotificationStandardReadService notificationStandardReadService;
  private final NotificationTemplateReadService notificationTemplateReadService;

  public List<Notification> findByMember(Long memberId) {
    return notificationRepository.findAllByMemberIdAndDeletedAtNull(
        memberId);
  }

  public List<Notification> makeNormals(Map<Member, List<Bookmark>> unreadBookmarks) {
    List<Notification> notifications = new ArrayList<>();
    List<NotificationTemplate> templates = notificationTemplateReadService.findAllByNotificationType(NotificationType.NORMAL);
    LocalDateTime now = TimezoneHandler.getUTCnow();

    for (Map.Entry<Member, List<Bookmark>> entry : unreadBookmarks.entrySet()) {
      Long memberId = entry.getKey().getId();
      NotificationStandard standard = notificationStandardReadService.findByMemberId(memberId);

      for (Bookmark bookmark : entry.getValue()) {
        LocalDateTime dueDateTime = getDueDateTime(bookmark, standard);
        LocalTime sendTime = makeSendTime(bookmark.getCreatedAt());
        if (isBeforeAndEqual(dueDateTime, now)) {
          notifications.add(
              Notification.makeNormalNotification(
                  memberId, bookmark,
                  getNotificationTitle(templates), getSendDateTime(dueDateTime, now, sendTime)
              )
          );
        }
      }

    }

    return notifications;
  }

  public List<Notification> getNotificationsToSend(LocalDateTime now) {
    return notificationRepository.getNotificationsToSend(now);
  }

  private LocalDateTime getDueDateTime(Bookmark bookmark, NotificationStandard standard) {
    LocalDateTime createdAt = bookmark.getCreatedAt();
    return createdAt.plusDays(standard.getNotifyStandardDay());
  }

  private LocalDateTime getSendDateTime(LocalDateTime dueDateTime, LocalDateTime now, LocalTime sendTime) {
    LocalDateTime firstSendDateTime = dueDateTime.toLocalDate().atTime(sendTime);
    if (now.isAfter(firstSendDateTime)) {
      return now.toLocalDate().atTime(sendTime);
    }
    return firstSendDateTime;
  }

  private LocalTime makeSendTime(LocalDateTime createdAt) {
    int createdMinute = createdAt.getMinute();
    int reservationMinute = createdMinute < 30 ? 0 : 30;
    return createdAt.toLocalTime().withMinute(reservationMinute).withSecond(0).withNano(0);
  }

  private boolean isBeforeAndEqual(LocalDateTime dueDateTime, LocalDateTime now) {
    return dueDateTime.isBefore(now) || dueDateTime.isEqual(now);
  }

  private String getNotificationTitle(List<NotificationTemplate> templates) {
    Random random = new Random();
    int randomIndex = random.nextInt(templates.size());
    return templates.get(randomIndex).getTitle();
  }

  public Notification findById(Long id) {
    return notificationRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(NotificationNotFoundException::new);
  }

}
