package org.pickly.service.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.notification.common.NotificationMapper;
import org.pickly.service.notification.controller.response.NotificationRes;
import org.pickly.service.notification.service.dto.NotificationDTO;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Notification", description = "알림 API")
public class NotificationController {

  private final NotificationService notificationService;
  private final NotificationMapper notificationMapper;

  @Operation(summary = "특정 유저의 알림 내역 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  @GetMapping("/members/{memberId}/notifications")
  public List<NotificationRes> findMemberNotifications(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    List<NotificationDTO> dtoList = notificationService.findMemberNotifications(memberId);
    return dtoList.stream().map(notificationMapper::toResponse).collect(Collectors.toList());
  }

  @Operation(summary = "특정 알림 읽기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PatchMapping("notifications/{notificationId}")
  public void readNotification(
      @Parameter(name = "notificationId", description = "알림 ID 값", example = "1", required = true)
      @Positive(message = "알림 ID는 양수입니다.") @PathVariable final Long notificationId
  ) {
    notificationService.readNotification(notificationId);
  }

  @Operation(summary = "특정 알림 삭제")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @DeleteMapping("notifications/{notificationId}")
  public void deleteNotification(
      @Parameter(name = "notificationId", description = "알림 ID 값", example = "1", required = true)
      @Positive(message = "알림 ID는 양수입니다.") @PathVariable final Long notificationId
  ) {
    notificationService.deleteNotification(notificationId);
  }

}
