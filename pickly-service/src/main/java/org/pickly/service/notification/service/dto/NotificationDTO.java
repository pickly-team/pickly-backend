package org.pickly.service.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDTO {

  private Long id;
  private String title;
  private String content;
  private Long bookmarkId;
  private Boolean isChecked;

}
