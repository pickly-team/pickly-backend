package org.pickly.service.domain.notification.service.standard;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.repository.interfaces.NotificationStandardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.pickly.service.domain.notification.exception.NotificationException.NotificationStandardAlreadyExistException;
import static org.pickly.service.domain.notification.exception.NotificationException.NotificationStandardNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationStandardReadService {

  private final NotificationStandardRepository notificationStandardRepository;

  public NotificationStandard findByMemberId(final Long memberId) {
    return notificationStandardRepository.findByMemberId(memberId)
        .orElseThrow(NotificationStandardNotFoundException::new);
  }

  public Integer findMyNotifyStandardDay(final Long memberId) {
    NotificationStandard standard = findByMemberId(memberId);
    return standard.getNotifyStandardDay();
  }

  public void checkByMemberId(Long memberId) {
    if (existsByMemberId(memberId)) {
      throw new NotificationStandardAlreadyExistException();
    }
  }

  public boolean existsByMemberId(Long memberId) {
    return notificationStandardRepository.existsByMemberId(memberId);
  }


}
