package org.pickly.service.domain.member.dto.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Member mode response")
public class MemberModeRes {

  @Schema(description = "하드 모드 사용 유저인가?", example = "true")
  private boolean isHardMode;

  @Schema(description = "알림 기준 일자. 00일 안에 읽지 않으면 알림 발생", example = "09:00")
  private LocalTime notifyDailyAt;

  public boolean getIsHardMode() {
    return this.isHardMode;
  }

}
