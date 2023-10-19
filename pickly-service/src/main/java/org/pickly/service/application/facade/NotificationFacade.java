package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.service.NotificationReadService;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationFacade {

  private final MemberReadService memberReadService;
  private final NotificationReadService notificationReadService;
  private final NotificationWriteService notificationWriteService;

  public void read(Long notificationId) {
    var notification = notificationReadService.findById(notificationId);
    notificationWriteService.read(notification);
  }

  public void readAllByMember(Long memberId) {
    var member = memberReadService.findById(memberId);
    notificationWriteService.readAllByMember(member);
  }

  public void delete(Long notificationId) {
    var notification = notificationReadService.findById(notificationId);
    notificationWriteService.delete(notification);
  }

  public void deleteAllByMember(Long memberId) {
    var member = memberReadService.findById(memberId);
    notificationWriteService.deleteByMember(member.getId());
  }

  public void deleteAllByIds(Long memberId, List<Long> notificationIds) {
    var member = memberReadService.findById(memberId);
    notificationWriteService.deleteByIds(member.getId(), notificationIds);
  }

}
