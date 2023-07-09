package org.pickly.service.member.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Member register response")
public class MemberRegisterRes {

  private String username;
  private Boolean isHardMode;
  private String email;
  private String name;
  private String nickname;
  private Long memberId;
}
