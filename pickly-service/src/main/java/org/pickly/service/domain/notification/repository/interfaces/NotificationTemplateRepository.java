package org.pickly.service.domain.notification.repository.interfaces;

import org.pickly.service.domain.notification.entity.NotificationTemplate;
import org.pickly.service.domain.notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

  List<NotificationTemplate> findAllByNotificationType(NotificationType type);

}
