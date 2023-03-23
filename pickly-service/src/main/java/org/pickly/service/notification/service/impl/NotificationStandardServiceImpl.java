package org.pickly.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.repository.interfaces.NotificationStandardRepository;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationStandardServiceImpl implements NotificationStandardService {

  private final NotificationStandardRepository notificationStandardRepository;

  @Override
  public NotificationStandard findByMember(final Long memberId) {
    return notificationStandardRepository.findByMemberId(memberId).orElseThrow(
        () -> new EntityNotFoundException("요청 member의 알림 기준이 존재하지 않습니다.")
    );
  }

}
