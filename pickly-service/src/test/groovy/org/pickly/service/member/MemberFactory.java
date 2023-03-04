package org.pickly.service.member;

import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.Password;

public class MemberFactory {

  public Member testMember() {
    return Member.builder()
        .username("picko123")
        .password(new Password("nobodyKnows123"))
        .isHardMode(false)
        .email("picko123@gmail.com")
        .name("picko")
        .nickname("iAmNotAPickyEater")
        .build();
  }

}
