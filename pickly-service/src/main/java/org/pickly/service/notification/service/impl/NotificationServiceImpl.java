package org.pickly.service.notification.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.common.NotificationMapper;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.repository.interfaces.NotificationRepository;
import org.pickly.service.notification.service.dto.NotificationDTO;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final MemberService memberService;
  private final NotificationMapper notificationMapper;

  @Override
  public List<NotificationDTO> findMemberNotifications(final Long memberId) {
    memberService.existsById(memberId);
    List<Notification> notifications = notificationRepository.findAllByMemberIdAndDeletedAtNull(
        memberId);
    return notifications.stream().map(notificationMapper::toDto).collect(Collectors.toList());
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

  public Notification findById(Long id) {
    return notificationRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 알림입니다."));
  }

}
