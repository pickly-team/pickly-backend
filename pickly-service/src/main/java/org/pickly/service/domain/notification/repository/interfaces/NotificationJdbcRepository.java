package org.pickly.service.domain.notification.repository.interfaces;

import org.pickly.service.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationJdbcRepository {

  void saveAll(List<Notification> notifications);

}
