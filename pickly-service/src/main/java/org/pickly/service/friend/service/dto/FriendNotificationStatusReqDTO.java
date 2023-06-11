package org.pickly.service.friend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendNotificationStatusReqDTO {

  private Long memberId;
  private Boolean isFollowing;

}
