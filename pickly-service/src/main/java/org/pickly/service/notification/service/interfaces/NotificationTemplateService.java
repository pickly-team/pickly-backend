package org.pickly.service.notification.service.interfaces;

import org.pickly.service.notification.entity.NotificationTemplate;
import org.pickly.service.notification.enums.NotificationType;

import java.util.List;

public interface NotificationTemplateService {

  List<NotificationTemplate> findAllByNotificationType(NotificationType type);

}
