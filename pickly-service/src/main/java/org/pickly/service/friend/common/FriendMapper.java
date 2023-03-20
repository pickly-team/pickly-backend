package org.pickly.service.friend.common;

import org.pickly.service.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.friend.controller.request.FriendNotificationStatusRes;
import org.pickly.service.friend.entity.FriendNotificationMode;
import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper {

  public FriendNotificationStatusReqDTO toDTO(Long memberId, FriendNotificationStatusReq request) {
    return new FriendNotificationStatusReqDTO(memberId, request.getNotificationAllowed());
  }

  public FriendNotificationStatusRes toFriendStatusDTO(FriendNotificationMode request) {
    return new FriendNotificationStatusRes(request);
  }

  public FriendNotificationStatusResDTO toFriendStatusResDTO(FriendNotificationMode request) {
    return new FriendNotificationStatusResDTO(request.isNotificationAllowed());
  }
}