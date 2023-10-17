package org.pickly.service.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.repository.interfaces.NotificationJdbcRepository;
import org.pickly.service.domain.notification.repository.interfaces.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationWriteService {

  private final NotificationRepository notificationRepository;
  private final NotificationJdbcRepository notificationJdbcRepository;

  public void deleteByBookmark(Long bookmarkId) {
    notificationRepository.deleteAllByBookmarkId(bookmarkId);
  }

  public void deleteByBookmark(List<Long> bookmarkIds) {
    notificationRepository.deleteAllByBookmarkIdIn(bookmarkIds);
  }

  public void deleteByMember(Long memberId) {
    notificationRepository.deleteAllByMemberId(memberId);
  }

  public void read(Notification notification) {
    notification.check();
  }

  public void readAllByMember(Member member) {
    notificationRepository.readAllByMember(member.getId());
  }

  public void save(List<Notification> notifications) {
    notificationJdbcRepository.saveAll(notifications);
  }

  public void updateAllToSend(List<Notification> notifications) {
    List<Long> ids = notifications.stream().map(Notification::getId).toList();
    notificationRepository.updateAllToSend(ids);
  }

  public void delete(Notification notification) {
    notification.delete();
  }

}
