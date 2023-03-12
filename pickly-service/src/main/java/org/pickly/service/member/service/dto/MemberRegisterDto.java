package org.pickly.service.member.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {
  private String username;
  private Boolean isHardMode;
  private String email;
  private String name;
  private String nickname;
}
