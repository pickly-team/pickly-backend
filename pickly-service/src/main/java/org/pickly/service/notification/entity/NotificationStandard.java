package org.pickly.service.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "notification_standard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationStandard extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @ColumnDefault("7")
  @Column(name = "standard_date", nullable = false)
  private Integer standardDate;

}
