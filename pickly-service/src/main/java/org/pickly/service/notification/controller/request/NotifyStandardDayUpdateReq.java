package org.pickly.service.notification.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Notification standard day update request")
public class NotifyStandardDayUpdateReq {

  @Schema(description = "북마크를 추가한 후 n일 이내 읽지 않았을 때 알림을 보낼 수 있도록 기준을 설정한다.", example = "3")
  @NotNull(message = "북마크 읽지 않음 알림 기준 일자를 입력해주세요")
  private Integer notifyStandardDay;

}
