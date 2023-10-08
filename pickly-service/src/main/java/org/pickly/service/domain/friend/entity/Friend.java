package org.pickly.service.domain.friend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
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

  @Builder
  Friend(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Member followee, Member follower, Boolean notificationEnabled
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.followee = followee;
    this.follower = follower;
    this.notificationEnabled = notificationEnabled;
  }

  public static Friend create(Member follower, Member followee) {
    return Friend.builder()
        .followee(followee)
        .follower(follower)
        .notificationEnabled(true)
        .build();
  }

  public void offNotification(Boolean notificationEnabled) {
    this.notificationEnabled = notificationEnabled;
  }

  public FriendNotificationMode getNotificationMode() {
    return notificationEnabled ? FriendNotificationMode.ALLOWED : FriendNotificationMode.NOT_ALLOWED;
  }
}
