package org.pickly.service.friend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "followee_id")
  private Member followee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "follower_id ")
  private Member follower;

  @Column(name = "notification_enabled", nullable = false)
  private Boolean notificationEnabled;

  public void enableNotification() {
    this.notificationEnabled = true;
  }

  public void disableNotification() {
    this.notificationEnabled = false;
  }

}
