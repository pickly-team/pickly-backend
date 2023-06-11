package org.pickly.service.notification.repository.interfaces;

import org.pickly.service.notification.entity.NotificationTemplate;
import org.pickly.service.notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

  List<NotificationTemplate> findAllByNotificationType(NotificationType type);

}
