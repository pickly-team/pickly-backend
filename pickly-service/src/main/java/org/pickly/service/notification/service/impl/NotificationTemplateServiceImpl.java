package org.pickly.service.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.notification.entity.NotificationTemplate;
import org.pickly.service.notification.enums.NotificationType;
import org.pickly.service.notification.repository.interfaces.NotificationTemplateRepository;
import org.pickly.service.notification.service.interfaces.NotificationTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

  private final NotificationTemplateRepository notificationTemplateRepository;

  @Override
  public List<NotificationTemplate> findAllByNotificationType(NotificationType type) {
    return notificationTemplateRepository.findAllByNotificationType(type);
  }
}
