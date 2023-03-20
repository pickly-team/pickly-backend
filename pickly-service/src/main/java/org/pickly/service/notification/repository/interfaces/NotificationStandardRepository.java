package org.pickly.service.notification.repository.interfaces;

import java.util.Optional;
import org.pickly.service.notification.entity.NotificationStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationStandardRepository extends JpaRepository<NotificationStandard, Long> {

  Optional<NotificationStandard> findByMemberId(Long memberId);

}
