package org.pickly.service.notification.repository.interfaces;

import org.pickly.service.notification.entity.Notification;

import java.util.List;

public interface NotificationJdbcRepository {

  void saveAll(List<Notification> notifications);

}
