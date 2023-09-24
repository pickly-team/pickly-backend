package org.pickly.service.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberMode {
  NORMAL("노말 모드"),
  HARD("하드 모드");

  private final String description;

  MemberMode(String description) {
    this.description = description;
  }

// FIXME: 굳이 enum으로..? boolean isHard로 충분 -> 프론트에게 변경 공수 문의

}
