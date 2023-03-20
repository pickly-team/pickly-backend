package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Member mode response")
public class MemberModeRes {

  @Schema(description = "하드 모드 사용 유저인가?", example = "true")
  private Boolean isHardMode;

  @Schema(description = "알림 기준 일자. 00일 안에 읽지 않으면 알림 발생", example = "3")
  private Integer standardDate;

}
