package org.pickly.service.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class MemberStatus {

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @Column(name = "is_inactive")
  private Boolean isInactive;

  public MemberStatus() {
    this.lastLoginAt = LocalDateTime.now();
    this.isInactive = false;
  }

  public MemberStatus(LocalDateTime lastLoginAt, Boolean isInactive) {
    this.lastLoginAt = lastLoginAt;
    this.isInactive = isInactive;
  }

  public void login() {
    this.lastLoginAt = LocalDateTime.now();
    this.isInactive = false;
  }

}
