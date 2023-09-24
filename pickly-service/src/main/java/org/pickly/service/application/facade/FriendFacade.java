package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.friend.entity.Friend;
import org.pickly.service.domain.friend.service.FriendReadService;
import org.pickly.service.domain.friend.service.FriendWriteService;
import org.pickly.service.domain.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendFacade {

  private final FriendReadService friendReadService;
  private final FriendWriteService friendWriteService;
  private final MemberReadService memberReadService;

  public void follow(Long fromMemberId, Long toMemberId) {
    friendReadService.checkAlreadyFriend(fromMemberId, toMemberId);

    var fromMember = memberReadService.findById(fromMemberId);
    var toMember = memberReadService.findById(toMemberId);

    friendWriteService.follow(fromMember, toMember);
  }

  public Friend setNotification(
      Long followerId, FriendNotificationStatusReqDTO request
  ) {
    var friend = friendReadService.findFollowerById(followerId, request.getMemberId());
    return friendWriteService.updateNotificationEnabled(friend, request.getIsFollowing());
  }

}
