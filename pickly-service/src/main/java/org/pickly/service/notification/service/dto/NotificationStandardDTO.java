package org.pickly.service.notification.service.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationStandardDTO {

  private boolean isActive;

  private LocalTime notifyDailyAt;
}
