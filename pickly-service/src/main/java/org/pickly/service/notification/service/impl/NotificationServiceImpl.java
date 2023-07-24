package org.pickly.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.common.NotificationMapper;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.entity.NotificationTemplate;
import org.pickly.service.notification.enums.NotificationType;
import org.pickly.service.notification.exception.NotificationException;
import org.pickly.service.notification.repository.interfaces.NotificationJdbcRepository;
import org.pickly.service.notification.repository.interfaces.NotificationRepository;
import org.pickly.service.notification.service.dto.NotificationDTO;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.pickly.service.notification.service.interfaces.NotificationTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

  private final NotificationJdbcRepository notificationJdbcRepository;
  private final NotificationRepository notificationRepository;
  private final MemberService memberService;
  private final NotificationStandardService notificationStandardService;
  private final NotificationTemplateService notificationTemplateService;
  private final NotificationMapper notificationMapper;

  @Override
  public List<NotificationDTO> findMemberNotifications(final Long memberId) {
    memberService.existsById(memberId);
    List<Notification> notifications = notificationRepository.findAllByMemberIdAndDeletedAtNull(
        memberId);
    return notifications.stream().map(notificationMapper::toDto).toList();
  }

  @Override
  public List<Notification> makeNormals(Map<Member, List<Bookmark>> unreadBookmarks) {
    List<Notification> notifications = new ArrayList<>();

    List<NotificationTemplate> templates = notificationTemplateService.findAllByNotificationType(NotificationType.NORMAL);
    LocalDateTime now = TimezoneHandler.getUTCnow();
    for (Map.Entry<Member, List<Bookmark>> entry : unreadBookmarks.entrySet()) {
      Long memberId = entry.getKey().getId();
      NotificationStandard standard = notificationStandardService.findByMemberId(memberId);

      for (Bookmark bookmark : entry.getValue()) {
        LocalDateTime dueDateTime = getDueDateTime(bookmark, standard);
        if (isBeforeAndEqual(dueDateTime, now)) {
          notifications.add(
              Notification.makeNormalNotification(
                  memberId, bookmark,
                  getNotificationTitle(templates), getSendDateTime(dueDateTime, standard)
              )
          );
        }
      }

    }

    return notifications;
  }

  @Override
  public List<Notification> getNotificationsToSend(LocalDateTime now) {
    return notificationRepository.getNotificationsToSend(now);
  }

  private LocalDateTime getDueDateTime(Bookmark bookmark, NotificationStandard standard) {
    LocalDateTime createdAt = bookmark.getCreatedAt();
    return createdAt.plusDays(standard.getNotifyStandardDay());
  }

  private LocalDateTime getSendDateTime(LocalDateTime dueDateTime, NotificationStandard standard) {
    return dueDateTime.toLocalDate().atTime(standard.getNotifyDailyAt());
  }

  private boolean isBeforeAndEqual(LocalDateTime dueDateTime, LocalDateTime now) {
    return dueDateTime.isBefore(now) || dueDateTime.isEqual(now);
  }

  private String getNotificationTitle(List<NotificationTemplate> templates) {
    Random random = new Random();
    int randomIndex = random.nextInt(templates.size());
    return templates.get(randomIndex).getTitle();
  }

  @Override
  @Transactional
  public void readNotification(final Long notificationId) {
    Notification notification = findById(notificationId);
    notification.check();
  }

  @Override
  @Transactional
  public void deleteNotification(final Long notificationId) {
    Notification notification = findById(notificationId);
    notification.delete();
  }

  @Override
  @Transactional
  public void saveAll(List<Notification> notifications) {
    notificationJdbcRepository.saveAll(notifications);
  }

  @Override
  @Transactional
  public void updateAllToSend(List<Notification> notifications) {
    List<Long> ids = notifications.stream().map(Notification::getId).toList();
    notificationRepository.updateAllToSend(ids);
  }


  public Notification findById(Long id) {
    return notificationRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(NotificationException.NotificationNotFoundException::new);
  }

}
