package org.pickly.service.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberModeDTO {

  private Boolean isHardMode;
  private Integer standardDate;

}
