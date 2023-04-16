package org.pickly.service.notification.repository.interfaces;

import java.util.List;
import org.pickly.service.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findAllByMemberId(Long memberId);

}
