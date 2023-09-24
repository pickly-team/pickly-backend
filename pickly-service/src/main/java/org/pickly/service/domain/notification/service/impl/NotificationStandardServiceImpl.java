package org.pickly.service.domain.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.service.interfaces.MemberReadService;
import org.pickly.service.domain.notification.exception.NotificationException;
import org.pickly.service.domain.notification.service.interfaces.NotificationStandardService;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.repository.interfaces.NotificationStandardRepository;
import org.pickly.service.domain.notification.service.dto.NotificationStandardDTO;
import org.pickly.service.domain.notification.service.dto.NotifyStandardDayUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationStandardServiceImpl implements NotificationStandardService {

  private final NotificationStandardRepository notificationStandardRepository;
  private final MemberReadService memberReadService;

  @Override
  @Transactional(readOnly = true)
  public NotificationStandard findByMemberId(final Long memberId) {
    return notificationStandardRepository.findByMemberId(memberId)
        .orElseThrow(NotificationException.NotificationStandardNotFoundException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public Integer findMyNotifyStandardDay(final Long memberId) {
    NotificationStandard standard = findByMemberId(memberId);
    return standard.getNotifyStandardDay();
  }

  @Override
  public void createNotificationStandard(Long memberId, NotificationStandardDTO dto) {
    if (notificationStandardRepository.existsByMemberId(memberId)) {
      throw new NotificationException.NotificationStandardAlreadyExistException();
    }

    notificationStandardRepository.save(
        new NotificationStandard(
            memberReadService.findById(memberId), dto.isActive(), 7, dto.getNotifyDailyAt()
        )
    );
  }

  @Override
  public void updateNotificationStandard(Long memberId, NotificationStandardDTO dto) {
    NotificationStandard notificationStandard = findByMemberId(memberId);
    notificationStandard.update(dto.isActive(), dto.getNotifyDailyAt());
  }

  @Override
  public void updateMyNotifyStandardDay(Long memberId, NotifyStandardDayUpdateDTO dto) {
    NotificationStandard notificationStandard = findByMemberId(memberId);
    notificationStandard.updateNotifyStandardDay(dto.getNotifyStandardDay());
  }
}
