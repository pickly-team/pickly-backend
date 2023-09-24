package org.pickly.service.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.pickly.service.domain.notification.repository.interfaces.NotificationStandardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
