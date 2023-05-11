package org.pickly.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.mapper.NotificationStandardMapper;
import org.pickly.service.notification.repository.interfaces.NotificationStandardRepository;
import org.pickly.service.notification.service.dto.NotificationStandardDTO;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationStandardServiceImpl implements NotificationStandardService {

  private final NotificationStandardRepository notificationStandardRepository;
  private final MemberService memberService;

  @Override
  @Transactional(readOnly = true)
  public NotificationStandard findByMemberId(final Long memberId) {
    return notificationStandardRepository.findByMemberId(memberId)
        .orElseThrow(() -> new EntityNotFoundException("요청 member의 알림 기준 설정이 존재하지 않습니다."));
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
      throw new BusinessException("이미 알림 기준 설정이 존재합니다.", ErrorCode.ENTITY_CONFLICT);
    }

    notificationStandardRepository.save(
        new NotificationStandard(
            memberService.findById(memberId), dto.isActive(), null, dto.getNotifyDailyAt()
        )
    );
  }

  @Override
  public void updateNotificationStandard(Long memberId, NotificationStandardDTO dto) {
    NotificationStandard notificationStandard = findByMemberId(memberId);
    notificationStandard.update(dto.isActive(), dto.getNotifyDailyAt());
  }
}
