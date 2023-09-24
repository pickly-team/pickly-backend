package org.pickly.service.domain.notification.repository.interfaces;

import java.util.Optional;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationStandardRepository extends JpaRepository<NotificationStandard, Long> {

  Optional<NotificationStandard> findByMemberId(Long memberId);

  boolean existsByMemberId(Long memberId);
}
