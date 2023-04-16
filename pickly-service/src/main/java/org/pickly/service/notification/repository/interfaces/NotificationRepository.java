package org.pickly.service.notification.repository.interfaces;

import lombok.RequiredArgsConstructor;
import org.pickly.service.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
