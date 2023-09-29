package org.pickly.service.domain.notification.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Notification standard update request")
public class NotificationStandardUpdateReq {

  @Schema(description = "Is notification activated", example = "true")
  @NotNull(message = "알림 활성화 여부를 입력해주세요.")
  private Boolean isActive;

  @Schema(description = "notify daily at", example = "09:00")
  @NotNull(message = "매일 알림 받을 시간을 입력해주세요.")
  private LocalTime notifyDailyAt;
}
