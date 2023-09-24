package org.pickly.service.domain.notification.service.dto;

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
  private long createdAt;

}
