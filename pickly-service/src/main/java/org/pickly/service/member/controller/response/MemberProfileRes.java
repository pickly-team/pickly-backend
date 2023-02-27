package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Member profile response")
public class MemberProfileRes {

  private String name;
  private String nickname;
  private String profileEmoji;
}
