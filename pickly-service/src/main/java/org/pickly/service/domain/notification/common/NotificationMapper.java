package org.pickly.service.domain.notification.common;

import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.notification.dto.controller.response.NotificationRes;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.dto.service.NotificationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

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

  public NotificationRes toResponse(Notification notification, String timezone) {
    long unixTimestamp = TimezoneHandler.convertToUnix(notification.getCreatedAt(), timezone);
    return NotificationRes.builder()
        .id(notification.getId())
        .title(notification.getTitle())
        .content(notification.getContent())
        .bookmarkId(notification.getBookmarkId())
        .isChecked(notification.isChecked())
        .createdAt(unixTimestamp)
        .build();
  }

  public List<NotificationRes> toResponse(List<Notification> notifications, Member member) {
    return notifications.stream().map(a -> toResponse(a, member.getTimezone())).toList();
  }

}
