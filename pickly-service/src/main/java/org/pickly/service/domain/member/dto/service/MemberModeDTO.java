package org.pickly.service.domain.member.dto.service;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberModeDTO {

  private Boolean isHardMode;
  private LocalTime notifyDailyAt;

}
