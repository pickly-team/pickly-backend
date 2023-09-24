package org.pickly.service.domain.friend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendNotificationStatusReqDTO {

  private Long memberId;
  private Boolean isFollowing;

}
