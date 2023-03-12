package org.pickly.service.member.entity;

import lombok.Getter;

@Getter
public enum MemberMode {
  NORMAL("노말 모드"),
  HARD("하드 모드");

  private final String description;

  MemberMode(String description) {
    this.description = description;
  }
}
