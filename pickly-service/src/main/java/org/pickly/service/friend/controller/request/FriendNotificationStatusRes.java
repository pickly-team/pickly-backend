package org.pickly.service.friend.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.friend.entity.FriendNotificationMode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendNotificationStatusRes {

  private FriendNotificationMode isNotificationAllowed;

}
