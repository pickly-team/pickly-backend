package org.pickly.service.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

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

  @Column(name = "notify_daily_at", nullable = false)
  private LocalTime notifyDailyAt;

  public void update(Boolean isActive, LocalTime notifyDailyAt) {
    this.isActive = isActive;
    this.notifyDailyAt = notifyDailyAt;
  }
}
