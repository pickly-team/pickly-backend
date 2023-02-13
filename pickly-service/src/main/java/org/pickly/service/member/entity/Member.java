package org.pickly.service.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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

  @Column(length = 20, nullable = false)
  private String name;

  @Column(length = 20, nullable = false, unique = true)
  private String nickname;

  @Column(name = "profile_emoji", columnDefinition = "text")
  private String profileEmoji;

}
