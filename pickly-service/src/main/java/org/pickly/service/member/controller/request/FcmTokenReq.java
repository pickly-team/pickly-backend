package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Member Fcm token")
public class FcmTokenReq {

  @NotBlank(message = "유저의 FCM 토큰을 입력해주세요.")
  @Schema(description = "fcm token", example = "jdafjlajl1213d!!3d~1")
  private String fcmToken;

}
