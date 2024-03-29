package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.service.standard.NotificationStandardReadService;
import org.pickly.service.domain.notification.service.standard.NotificationStandardWriteService;
import org.pickly.service.domain.notification.dto.service.NotificationStandardDTO;
import org.pickly.service.domain.notification.dto.service.NotifyStandardDayUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationStandardFacade {

  private final MemberReadService memberReadService;
  private final NotificationStandardReadService notificationStandardReadService;
  private final NotificationStandardWriteService notificationStandardWriteService;

  private static final int DEFAULT_STANDARD_DAY = 7;

  public void create(Long memberId, NotificationStandardDTO request) {
    notificationStandardReadService.checkByMemberId(memberId);
    var member = memberReadService.findById(memberId);


    NotificationStandard standard = NotificationStandard.create(
        member, request.isActive(), request.getNotifyDailyAt()
    );
    notificationStandardWriteService.create(standard);
  }

  public void update(Long memberId, NotificationStandardDTO request) {
    var standard = notificationStandardReadService.findByMemberId(memberId);
    notificationStandardWriteService.update(standard, request.isActive(), request.getNotifyDailyAt());
  }

  public void updateNotifyStandardDay(Long memberId, NotifyStandardDayUpdateDTO dto) {
    var standard = notificationStandardReadService.findByMemberId(memberId);
    notificationStandardWriteService.updateNotifyStandardDay(standard, dto.getNotifyStandardDay());
  }


}
