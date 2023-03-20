package org.pickly.service.member.notification.repository.interfaces;

import java.util.Optional;
import org.pickly.service.member.notification.entity.NotificationStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationStandardRepository extends JpaRepository<NotificationStandard, Long> {

  Optional<NotificationStandard> findByMemberId(Long memberId);

}
