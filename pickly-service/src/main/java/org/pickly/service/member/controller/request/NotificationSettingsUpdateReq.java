package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Notification update request")
public class NotificationSettingsUpdateReq {

  @NotBlank(message = "유저의 timezone을 입력해주세요.")
  @Schema(description = "timezone", example = "Asia/Seoul")
  private String timezone;

}
