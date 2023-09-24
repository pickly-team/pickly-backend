package org.pickly.service.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.validator.timezone.TimezoneCheck;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Notification update request")
public class NotificationSettingsUpdateReq {

  @TimezoneCheck
  @NotBlank(message = "유저의 timezone을 입력해주세요.")
  @Schema(description = "timezone", example = "Asia/Seoul")
  private String timezone;

  @NotBlank(message = "유저의 fcm token을 입력해주세요.")
  @Schema(description = "fcmToken", example = "aedjfajglk1249d~~")
  private String fcmToken;

}
