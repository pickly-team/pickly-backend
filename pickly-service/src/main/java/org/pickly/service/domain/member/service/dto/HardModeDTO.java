package org.pickly.service.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HardModeDTO {

  private String isHardMode;

  public HardModeDTO(String isHardMode) {
    this.isHardMode = isHardMode;
  }
}