package org.pickly.service.domain.friend.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pickly.service.domain.friend.entity.FriendNotificationMode;

@Getter
@AllArgsConstructor
public class FriendNotificationStatusResDTO {

  private Boolean isNotificationAllowed;

  public FriendNotificationMode getNotificationMode(boolean isNotificationAllowed) {
    return isNotificationAllowed ? FriendNotificationMode.ALLOWED
        : FriendNotificationMode.NOT_ALLOWED;
  }
}
