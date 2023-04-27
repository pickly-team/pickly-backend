package org.pickly.service.member.service.dto;

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
