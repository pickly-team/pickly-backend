package org.pickly.service.domain.friend.common;

import org.pickly.service.domain.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.domain.friend.controller.response.FollowerRes;
import org.pickly.service.domain.friend.controller.response.FollowingRes;
import org.pickly.service.domain.friend.controller.response.FriendNotificationStatusRes;
import org.pickly.service.domain.friend.entity.Friend;
import org.pickly.service.domain.friend.entity.FriendNotificationMode;
import org.pickly.service.domain.friend.service.dto.FollowerResDTO;
import org.pickly.service.domain.friend.service.dto.FollowingResDTO;
import org.pickly.service.domain.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.domain.friend.service.dto.FriendNotificationStatusResDTO;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper {

  public FriendNotificationStatusReqDTO toDTO(Long memberId, FriendNotificationStatusReq request) {
    return new FriendNotificationStatusReqDTO(memberId, request.getNotificationAllowed());
  }

  public FriendNotificationStatusResDTO toFriendStatusResDTO(FriendNotificationMode request) {
    return new FriendNotificationStatusResDTO(request.isNotificationAllowed());
  }

  public FriendNotificationStatusRes toResponse(Friend friend) {
    return new FriendNotificationStatusRes(friend.getNotificationMode());
  }

  public FollowerRes toFollowerRes(FollowerResDTO dto) {
    return FollowerRes.builder()
        .memberId(dto.getMemberId())
        .nickname(dto.getNickname())
        .isFollowing(dto.getIsFollowing())
        .emoji(dto.getEmoji())
        .build();
  }

  public FollowingRes toFollowingRes(FollowingResDTO dto) {
    return new FollowingRes(dto.getMemberId(), dto.getNickname(), dto.getEmoji());
  }

}
