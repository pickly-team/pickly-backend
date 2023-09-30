package org.pickly.service.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "notification_standard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationStandard extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "member_id", nullable = false, unique = true)
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "notify_standard_day", nullable = false)
  @ColumnDefault("7")
  private Integer notifyStandardDay;

  @Column(name = "notify_daily_at", nullable = false)
  private LocalTime notifyDailyAt;

  @Builder
  public NotificationStandard(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Member member, Boolean isActive, Integer notifyStandardDay, LocalTime notifyDailyAt
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.member = member;
    this.isActive = isActive;
    this.notifyStandardDay = notifyStandardDay;
    this.notifyDailyAt = notifyDailyAt;
  }

  public static NotificationStandard create(Member member) {
    return NotificationStandard.builder()
        .member(member).isActive(true)
        .notifyStandardDay(7)
        .notifyDailyAt(LocalTime.of(9, 0))
        .build();
  }

  public static NotificationStandard create(
      Member member, boolean isActive, LocalTime notifyDailyAt
  ) {
    return NotificationStandard.builder()
        .member(member).isActive(isActive)
        .notifyStandardDay(7).notifyDailyAt(notifyDailyAt)
        .build();
  }

  public void update(Boolean isActive, LocalTime notifyDailyAt) {
    this.isActive = isActive;
    this.notifyDailyAt = notifyDailyAt;
  }

  public void updateNotifyStandardDay(Integer notifyStandardDay) {
    this.notifyStandardDay = notifyStandardDay;
  }

}
