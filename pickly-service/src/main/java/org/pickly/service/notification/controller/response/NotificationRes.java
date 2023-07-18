package org.pickly.service.notification.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationRes {

  @Schema(description = "Notification ID", example = "1")
  private Long id;

  @Schema(description = "Notification title", example = "알림이 왔어요!")
  private String title;

  @Schema(description = "Notification content", example = "3일 전 추가한 000 북마크를 읽어봅시다!")
  private String content;

  @Schema(description = "Bookmark ID", example = "112")
  private Long bookmarkId;

  @Schema(description = "Is member read this notification?", example = "true")
  private Boolean isChecked;

  @Schema(description = "notification create time : utc timestamp", example = "12131411")
  private long createdAt;

}
