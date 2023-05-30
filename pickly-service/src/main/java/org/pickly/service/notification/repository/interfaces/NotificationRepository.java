package org.pickly.service.notification.repository.interfaces;

import java.util.List;
import java.util.Optional;
import org.pickly.service.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findAllByMemberIdAndDeletedAtNull(Long memberId);

  Optional<Notification> findByIdAndDeletedAtNull(Long id);
}
