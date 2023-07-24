package org.pickly.service.member;

import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.Password;

public class MemberFactory {

  public Member testMember(String username, String email, String name, String nickname,
      String profileEmoji) {
    return Member.builder()
        .username(username)
        .password(new Password("nobodyKnows123"))
        .isHardMode(false)
        .email(email)
        .name(name)
        .nickname(nickname)
        .profileEmoji(profileEmoji)
        .fcmToken("test1234566")
        .timezone("UTC")
        .build();
  }

  public Member testMember() {
    return testMember("picko123", "picko123@gmail.com", "picko",
        "iAmNotAPickyEater", "👍");
  }
}
