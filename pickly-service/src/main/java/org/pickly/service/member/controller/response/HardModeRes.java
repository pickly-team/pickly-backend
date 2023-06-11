package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Hard mode response")
public class HardModeRes {

  @Schema(description = "현재 어떤 모드를 사용중인가?", example = "true")
  private String isHardMode;

  public String getIsHardMode() {
    return this.isHardMode;
  }

}
