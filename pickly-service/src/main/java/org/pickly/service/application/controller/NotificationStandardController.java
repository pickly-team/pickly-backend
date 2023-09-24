package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.NotificationStandardFacade;
import org.pickly.service.domain.notification.controller.request.NotificationStandardCreateReq;
import org.pickly.service.domain.notification.controller.request.NotifyStandardDayUpdateReq;
import org.pickly.service.domain.notification.controller.response.NotificationStandardRes;
import org.pickly.service.domain.notification.mapper.NotificationStandardMapper;
import org.pickly.service.domain.notification.service.standard.NotificationStandardReadService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-standards")
@RequiredArgsConstructor
@Validated
@Tag(name = "Notification Standard", description = "알림 기준 설정 API")
public class NotificationStandardController {

  private final NotificationStandardReadService notificationStandardReadService;
  private final NotificationStandardFacade notificationStandardFacade;
  private final NotificationStandardMapper notificationStandardMapper;

  @GetMapping("/me")
  @Operation(summary = "내 알림 기준을 조회한다.")
  public NotificationStandardRes findMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId
  ) {
    return notificationStandardMapper.toResponse(
        notificationStandardReadService.findByMemberId(loginId));
  }

  @GetMapping("/unread-bookmark/me")
  @Operation(summary = "내 '읽지 않은 북마크 알림 기준 일자' 설정을 조회한다.")
  public Integer findMyNotifyStandardDay(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId
  ) {
    return notificationStandardReadService.findMyNotifyStandardDay(loginId);
  }

  @PostMapping
  @Operation(summary = "내 알림 기준을 생성한다.")
  public void createMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId,

      @RequestBody
      @Valid
      NotificationStandardCreateReq request
  ) {
    notificationStandardFacade.create(
        loginId,
        notificationStandardMapper.toCreateDTO(request)
    );
  }

  @PutMapping("/me")
  @Operation(summary = "내 알림 기준을 수정한다.")
  public void updateMyNotificationStandard(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId,

      @RequestBody
      @Valid
      NotificationStandardCreateReq request
  ) {
    notificationStandardFacade.update(
        loginId,
        notificationStandardMapper.toUpdateDTO(request)
    );
  }

  @PatchMapping("/unread-bookmark/me")
  @Operation(summary = "내 '읽지 않은 북마크 알림 기준 일자' 설정을 수정한다.")
  public void updateMyNotifyStandardDay(
      @RequestParam
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long loginId,

      @RequestBody
      @Valid
      NotifyStandardDayUpdateReq request
  ) {
    notificationStandardFacade.updateNotifyStandardDay(
        loginId,
        notificationStandardMapper.toDto(request)
    );
  }

}
