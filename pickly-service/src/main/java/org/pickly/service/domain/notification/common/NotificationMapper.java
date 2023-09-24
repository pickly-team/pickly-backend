package org.pickly.service.domain.notification.common;

import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.controller.response.NotificationRes;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.service.dto.NotificationDTO;
import org.springframework.stereotype.Component;

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

  public NotificationDTO toDto(Notification entity, Member member) {
    long unixTimestamp = TimezoneHandler.convertToUnix(entity.getCreatedAt(), member.getTimezone());
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
