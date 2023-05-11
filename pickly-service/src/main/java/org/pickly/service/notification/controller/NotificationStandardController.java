package org.pickly.service.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.notification.controller.request.NotificationStandardCreateReq;
import org.pickly.service.notification.controller.response.NotificationStandardRes;
import org.pickly.service.notification.mapper.NotificationStandardMapper;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification-standards")
@RequiredArgsConstructor
@Validated
@Tag(name = "NotificationStandard", description = "알림 기준 설정 API")
public class NotificationStandardController {

  private final NotificationStandardService notificationStandardService;
  private final NotificationStandardMapper notificationStandardMapper;

  @GetMapping("/me")
  @Operation(summary = "내 알림 기준 조회")
  public NotificationStandardRes findMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId
  ) {
    return notificationStandardMapper.toResponse(
        notificationStandardService.findByMemberId(loginId));
  }

  @PostMapping
  @Operation(summary = "알림 기준 생성")
  public void createMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId,

      @RequestBody
      @Valid
      NotificationStandardCreateReq request
  ) {
    notificationStandardService.createNotificationStandard(
        loginId,
        notificationStandardMapper.toCreateDTO(request)
    );
  }

  @PutMapping("/me")
  @Operation(summary = "내 알림 기준 수정")
  public void updateMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId,

      @RequestBody
      @Valid
      NotificationStandardCreateReq request
  ) {
    notificationStandardService.updateNotificationStandard(
        loginId,
        notificationStandardMapper.toUpdateDTO(request)
    );
  }
}