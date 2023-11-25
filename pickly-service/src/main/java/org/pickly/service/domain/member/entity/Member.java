package org.pickly.service.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Column(length = 80, nullable = false, unique = true)
  private String username;

  @Embedded
  private Password password;

  @Column(name = "is_hard_mode", nullable = false)
  private Boolean isHardMode;

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @Column(length = 20)
  private String name;

  @Column(length = 200, unique = true)
  private String nickname;

  @Column(name = "profile_emoji", columnDefinition = "text")
  private String profileEmoji;

  @Column(name = "fcm_token", length = 200)
  private String fcmToken;

  @Column(name = "timezone", length = 20)
  private String timezone;

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @Builder
  public Member(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      String username, Password password, Boolean isHardMode,
      String email, String name, String nickname, String profileEmoji,
      String fcmToken, String timezone, LocalDateTime lastLoginAt
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.username = username;
    this.password = password;
    this.isHardMode = isHardMode;
    this.email = email;
    this.name = name;
    this.nickname = nickname;
    this.profileEmoji = profileEmoji;
    this.fcmToken = fcmToken;
    this.timezone = timezone;
    this.lastLoginAt = lastLoginAt;
  }

  public void updateLastLoginAt() {
    this.lastLoginAt = LocalDateTime.now();
  }

  public void updateProfile(String name, String nickname, String profileEmoji) {
    this.name = name;
    this.nickname = nickname;
    this.profileEmoji = profileEmoji;
  }

  public void updateNotificationSettings(String fcmToken, String timezone) {
    this.fcmToken = fcmToken;
    this.timezone = timezone;
  }

  public void toHardMode(Boolean isHardMode) {
    this.isHardMode = isHardMode;
  }

  public MemberMode isHardMode(Boolean isHardMode) {
    return isHardMode ? MemberMode.HARD : MemberMode.NORMAL;
  }

  public void handleNullName() {
    if (name == null) {
      this.name = "";
    }
  }

  public void handleNullNickname() {
    if (nickname == null) {
      this.nickname = "";
    }
  }
}
