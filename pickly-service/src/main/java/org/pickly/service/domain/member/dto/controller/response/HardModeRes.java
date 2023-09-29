package org.pickly.service.domain.member.dto.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.domain.member.entity.MemberMode;

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

  public HardModeRes(MemberMode mode) {
    this.isHardMode = mode.getDescription();
  }

}
