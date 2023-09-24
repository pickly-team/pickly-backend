package org.pickly.service.domain.notification.service.standard;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.repository.interfaces.NotificationStandardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationStandardWriteService {

  private final NotificationStandardRepository notificationStandardRepository;

  public void create(Member member) {
    notificationStandardRepository.save(
        NotificationStandard.createDafaultStandard(member)
    );
  }

  public void create(NotificationStandard standard) {
    notificationStandardRepository.save(standard);
  }

  public void update(NotificationStandard standard, boolean isActive, LocalTime notifyDailyAt) {
    standard.update(isActive, notifyDailyAt);
  }

  public void updateNotifyStandardDay(NotificationStandard standard, Integer notifyStandardDay) {
    standard.updateNotifyStandardDay(notifyStandardDay);
  }

}
