package org.pickly.service.domain.member.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberProfileUpdateDTO {

  private String name;
  private String nickname;
  private String profileEmoji;
}
