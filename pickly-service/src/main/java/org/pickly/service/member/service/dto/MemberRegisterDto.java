package org.pickly.service.member.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pickly.service.member.entity.Password;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {
  private String username;
  private Boolean isHardMode;
  private String email;
  private String name;
  private String nickname;
  private Password password;
}
