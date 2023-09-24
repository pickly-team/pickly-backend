package org.pickly.service.domain.notification.service.interfaces;

import org.pickly.service.domain.notification.entity.NotificationTemplate;
import org.pickly.service.domain.notification.enums.NotificationType;

import java.util.List;

public interface NotificationTemplateService {

  List<NotificationTemplate> findAllByNotificationType(NotificationType type);

}
