package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.NotificationFacade;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.common.NotificationMapper;
import org.pickly.service.domain.notification.dto.controller.response.NotificationRes;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.service.NotificationReadService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Notification", description = "알림 API")
public class NotificationController {

  private final MemberReadService memberReadService;
  private final NotificationReadService notificationReadService;
  private final NotificationFacade notificationFacade;
  private final NotificationMapper notificationMapper;

  @GetMapping("/members/{memberId}/notifications")
  @Operation(summary = "특정 유저의 알림 내역을 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public List<NotificationRes> findMemberNotifications(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    var member = memberReadService.findById(memberId);
    List<Notification> notifications = notificationReadService.findByMember(memberId);
    return notificationMapper.toResponse(notifications, member);
  }

  @PatchMapping("notifications/{notificationId}")
  @Operation(summary = "특정 알림을 읽기 처리한다.")
  public void readNotification(
      @Parameter(name = "notificationId", description = "알림 ID 값", example = "1", required = true)
      @Positive(message = "알림 ID는 양수입니다.") @PathVariable final Long notificationId
  ) {
    notificationFacade.read(notificationId);
  }

  @PatchMapping("/members/{memberId}/notifications")
  @Operation(summary = "특정 유저의 모든 알림을 읽기 처리한다.")
  public void readAllByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    notificationFacade.readAllByMember(memberId);
  }

  @DeleteMapping("notifications/{notificationId}")
  @Operation(summary = "특정 알림을 삭제한다.")
  public void deleteNotification(
      @Parameter(name = "notificationId", description = "알림 ID 값", example = "1", required = true)
      @Positive(message = "알림 ID는 양수입니다.") @PathVariable final Long notificationId
  ) {
    notificationFacade.delete(notificationId);
  }

  @DeleteMapping("/members/{memberId}/notifications")
  @Operation(summary = "특정 유저의 모든 알림을 삭제한다.")
  public void deleteAllByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    notificationFacade.deleteAllByMember(memberId);
  }

  @DeleteMapping("/members/{memberId}/notifications/selected")
  @Operation(summary = "특정 유저가 선택한 알림을 삭제한다.")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAllByIds(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @RequestParam(value = "notificationIds") List<Long> notificationIds
  ) {
    notificationFacade.deleteAllByIds(memberId, notificationIds);
  }

}
