package org.pickly.service.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationDTO {

  private Long id;
  private String title;
  private String content;

}
