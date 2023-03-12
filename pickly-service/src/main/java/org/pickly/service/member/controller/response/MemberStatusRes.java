package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Member mode status response")
public class MemberStatusRes {

  @Schema(description = "member mode status normal or hard", example = "노말 모드 or 하드 모드")
  private String userMode;
}
