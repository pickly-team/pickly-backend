package org.pickly.service.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

  @Column(length = 20, unique = true)
  private String nickname;

  @Column(name = "profile_emoji", columnDefinition = "text")
  private String profileEmoji;

  @Column(name = "fcm_token", length = 200)
  private String fcmToken;

  @Column(name = "timezone", length = 20)
  private String timezone;

  public void updateToken(final String newToken) {
    this.fcmToken = newToken;
  }

  public void updateProfile(String name, String nickname, String profileEmoji) {
    this.name = name;
    this.nickname = nickname;
    this.profileEmoji = profileEmoji;
  }

  public void toHardMode(Boolean isHardMode) {
    this.isHardMode = isHardMode;
  }

  public MemberMode isHardMode(Boolean isHardMode) {
    return isHardMode ? MemberMode.HARD : MemberMode.NORMAL;
  }

  public void updateNotificationSettings(String fcmToken, String timezone) {
    this.fcmToken = fcmToken;
    this.timezone = timezone;
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
