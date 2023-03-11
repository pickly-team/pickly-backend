package org.pickly.service.friend;

import org.pickly.service.friend.entity.Friend;
import org.pickly.service.member.entity.Member;

public class FriendFactory {

  public Friend testFriend(Member follower, Member followee) {
    return Friend.builder()
        .follower(follower)
        .followee(followee)
        .notificationEnabled(true)
        .build();
  }
}
