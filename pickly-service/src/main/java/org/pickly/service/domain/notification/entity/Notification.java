package org.pickly.service.domain.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.notification.enums.NotificationType;
import org.pickly.service.domain.notification.enums.NotificationTypeConverter;

import java.time.LocalDateTime;

@Entity
@Getter
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

  @Builder
  public Notification(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Long memberId, Long bookmarkId, String title, String content, boolean isChecked, boolean isSend,
      LocalDateTime sendDateTime, NotificationType notificationType
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.memberId = memberId;
    this.bookmarkId = bookmarkId;
    this.title = title;
    this.content = content;
    this.isChecked = isChecked;
    this.isSend = isSend;
    this.sendDateTime = sendDateTime;
    this.notificationType = notificationType;
  }

  public void check() {
    this.isChecked = true;
  }

  public static Notification makeNormal(
      Long memberId, Bookmark bookmark, String title, LocalDateTime sendDateTime
  ) {
    return Notification.builder()
        .memberId(memberId)
        .bookmarkId(bookmark.getId())
        .title(title)
        .content(bookmark.getTitle())
        .isChecked(false)
        .isSend(false)
        .sendDateTime(sendDateTime)
        .notificationType(NotificationType.NORMAL)
        .build();
  }

}
