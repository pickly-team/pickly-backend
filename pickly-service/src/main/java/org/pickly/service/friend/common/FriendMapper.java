package org.pickly.service.friend.common;

import org.pickly.service.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.friend.controller.response.FollowerRes;
import org.pickly.service.friend.controller.response.FriendNotificationStatusRes;
import org.pickly.service.friend.controller.response.MemberFollowerInfoRes;
import org.pickly.service.friend.entity.FriendNotificationMode;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.friend.service.dto.MemberFollowerInfoResDTO;
import org.springframework.stereotype.Component;

import java.util.List;

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

  public MemberFollowerInfoRes toMemberFollowerInfoRes(MemberFollowerInfoResDTO dto) {
    List<FollowerRes> followerResList = dto.getFollowerList().stream().map(this::toFollowerRes).toList();
    return MemberFollowerInfoRes.builder()
        .count(dto.getCount())
        .followerList(followerResList)
        .build();
  }

  public FollowerRes toFollowerRes(FollowerResDTO dto) {
    return FollowerRes.builder()
        .memberId(dto.getMemberId())
        .loginId(dto.getLoginId())
        .isFollowing(dto.getIsFollowing())
        .build();
  }

}
