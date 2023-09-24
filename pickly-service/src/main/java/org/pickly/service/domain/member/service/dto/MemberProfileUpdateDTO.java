package org.pickly.service.domain.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberProfileUpdateDTO {

  private String name;
  private String nickname;
  private String profileEmoji;
}
