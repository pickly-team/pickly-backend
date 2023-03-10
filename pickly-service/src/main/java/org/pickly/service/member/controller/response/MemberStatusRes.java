package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Member mode status response")
public class MemberStatusRes {

  @Schema(description = "member mode status success or failed", example = "true or false")
  private Boolean isSuccess;
}
