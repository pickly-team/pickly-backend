package org.pickly.service.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

import java.time.LocalTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "notification_standard")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationStandard extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false, unique = true)
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "notify_standard_day", nullable = false)
  @ColumnDefault("7")
  private Integer notifyStandardDay;

  @Column(name = "notify_daily_at", nullable = false)
  private LocalTime notifyDailyAt;

  @PrePersist
  public void prePersist() {
    if (this.notifyStandardDay == null) {
      this.notifyStandardDay = 7;
    }
  }

  public void update(Boolean isActive, LocalTime notifyDailyAt) {
    this.isActive = isActive;
    this.notifyDailyAt = notifyDailyAt;
  }

  public void updateNotifyStandardDay(Integer notifyStandardDay) {
    this.notifyStandardDay = notifyStandardDay;
  }

}
