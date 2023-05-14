package org.pickly.service.friend.service.interfaces;

import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.friend.service.dto.MemberFollowerInfoResDTO;

public interface FriendService {

  void follow(Long followerId, Long memberId);

  void unfollow(Long followerId, Long memberId);

  FriendNotificationStatusResDTO setNotification(Long followerId,
      FriendNotificationStatusReqDTO request);

  MemberFollowerInfoResDTO findAllFollowerByMember(Long memberId);
}
