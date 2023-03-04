package org.pickly.service.friend.service.interfaces;

public interface FriendService {

  void follow(Long followerId, Long memberId);

  void unfollow(Long followerId, Long memberId);

}
