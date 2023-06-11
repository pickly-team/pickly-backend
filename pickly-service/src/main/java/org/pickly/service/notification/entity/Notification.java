package org.pickly.service.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.notification.enums.NotificationType;
import org.pickly.service.notification.enums.NotificationTypeConverter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "bookmark_id", nullable = false)
  private Long bookmarkId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(name = "is_checked", nullable = false)
  private boolean isChecked;

  @Column(name = "is_send", nullable = false)
  private boolean isSend;

  @Column(name = "send_date_time", nullable = false)
  private LocalDateTime sendDateTime;

  @Column(name = "notification_type", nullable = false)
  @Convert(converter = NotificationTypeConverter.class)
  private NotificationType notificationType;

  public void check() {
    this.isChecked = true;
  }

}
