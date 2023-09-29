package org.pickly.service.domain.friend.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.domain.friend.entity.FriendNotificationMode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendNotificationStatusRes {

  private FriendNotificationMode isNotificationAllowed;

}
