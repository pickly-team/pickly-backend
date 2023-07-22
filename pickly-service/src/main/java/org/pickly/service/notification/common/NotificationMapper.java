package org.pickly.service.notification.common;

import org.pickly.service.notification.controller.response.NotificationRes;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.service.dto.NotificationDTO;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class NotificationMapper {

  public NotificationRes toResponse(NotificationDTO dto) {
    return NotificationRes.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .content(dto.getContent())
        .bookmarkId(dto.getBookmarkId())
        .isChecked(dto.getIsChecked())
        .createdAt(dto.getCreatedAt())
        .build();
  }

  public NotificationDTO toDto(Notification entity) {
    ZonedDateTime kstDateTime = entity.getCreatedAt().atZone(ZoneOffset.of("+09:00"));
    long unixTimestamp = kstDateTime.toEpochSecond();
    return NotificationDTO.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .content(entity.getContent())
        .bookmarkId(entity.getBookmarkId())
        .isChecked(entity.isChecked())
        .createdAt(unixTimestamp)
        .build();
  }

}
