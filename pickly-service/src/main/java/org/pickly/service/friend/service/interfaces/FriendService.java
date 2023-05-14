package org.pickly.service.friend.service.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;

import java.util.List;

public interface FriendService {

  void follow(Long followerId, Long memberId);

  void unfollow(Long followerId, Long memberId);

  Long countFollowerByMember(Long memberId);

  Long countFolloweeByMember(Long memberId);

  FriendNotificationStatusResDTO setNotification(Long followerId,
      FriendNotificationStatusReqDTO request);

  List<FollowerResDTO> findAllFollowerByMember(Long memberId, PageRequest pageRequest);
}
