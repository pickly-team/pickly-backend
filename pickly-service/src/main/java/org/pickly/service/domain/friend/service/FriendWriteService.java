package org.pickly.service.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.friend.entity.Friend;
import org.pickly.service.domain.friend.repository.interfaces.FriendRepository;
import org.pickly.service.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendWriteService {

  private final FriendRepository friendRepository;
  private static final boolean FRIEND_NOTIFICATION_ON = true;

  public void follow(Member fromMember, Member toMember) {
    Friend friend = new Friend(toMember, fromMember, FRIEND_NOTIFICATION_ON);
    friendRepository.save(friend);
  }

  public void unfollow(Long fromMemberId, Long toMemberId) {
    friendRepository.unfollow(fromMemberId, toMemberId);
  }

  public Friend updateNotificationEnabled(Friend friend, boolean isFollowing) {
    friend.updateNotificationEnabled(isFollowing);
    return friend;
  }

}
