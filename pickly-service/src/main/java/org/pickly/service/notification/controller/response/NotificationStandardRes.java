package org.pickly.service.notification.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Notification standard response")
public class NotificationStandardRes {

  @Schema(description = "is active?", example = "true")
  private Boolean isActive;

  @Schema(description = "notify daily at", example = "09:00")
  private LocalTime notifyDailyAt;
}
