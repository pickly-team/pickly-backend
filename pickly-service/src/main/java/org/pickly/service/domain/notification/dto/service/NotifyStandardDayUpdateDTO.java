package org.pickly.service.domain.notification.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotifyStandardDayUpdateDTO {

  private Integer notifyStandardDay;

}
