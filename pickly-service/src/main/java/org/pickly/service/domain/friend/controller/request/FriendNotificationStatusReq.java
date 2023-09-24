package org.pickly.service.domain.friend.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Friend status request")
public class FriendNotificationStatusReq {

  private Boolean notificationAllowed;
}
