package org.pickly.service.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.notification.enums.NotificationType;
import org.pickly.service.notification.enums.NotificationTypeConverter;

@Entity
@Getter
@Table(name = "notification_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationTemplate extends BaseEntity {

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(name = "notification_type", nullable = false)
  @Convert(converter = NotificationTypeConverter.class)
  private NotificationType notificationType;

}
