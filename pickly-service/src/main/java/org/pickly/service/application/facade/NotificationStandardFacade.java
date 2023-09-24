package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.service.NotificationStandardReadService;
import org.pickly.service.domain.notification.service.NotificationStandardWriteService;
import org.pickly.service.domain.notification.service.dto.NotificationStandardDTO;
import org.pickly.service.domain.notification.service.dto.NotifyStandardDayUpdateDTO;
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

    NotificationStandard standard = new NotificationStandard(
        member, request.isActive(), DEFAULT_STANDARD_DAY, request.getNotifyDailyAt()
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
