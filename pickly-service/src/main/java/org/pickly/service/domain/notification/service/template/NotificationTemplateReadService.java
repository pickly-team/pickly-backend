package org.pickly.service.domain.notification.service.template;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.notification.entity.NotificationTemplate;
import org.pickly.service.domain.notification.enums.NotificationType;
import org.pickly.service.domain.notification.repository.interfaces.NotificationTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationTemplateReadService {

  private final NotificationTemplateRepository notificationTemplateRepository;

  public List<NotificationTemplate> findAllByNotificationType(NotificationType type) {
    return notificationTemplateRepository.findAllByNotificationType(type);
  }
}
