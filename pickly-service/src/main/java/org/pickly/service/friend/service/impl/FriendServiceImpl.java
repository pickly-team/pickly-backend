package org.pickly.service.friend.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.friend.entity.Friend;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendServiceImpl implements FriendService {

  static final boolean FRIEND_ALERT_ON = true;
  private final FriendRepository friendRepository;
  private final MemberService memberService;

  @Override
  @Transactional
  public void follow(final Long followerId, final Long memberId) {
    checkAlreadyFriend(followerId, memberId);
    Member follower = memberService.findById(followerId);
    Member followee = memberService.findById(memberId);
    friendRepository.save(new Friend(followee, follower, FRIEND_ALERT_ON));
  }

  @Override
  @Transactional
  public void unfollow(final Long followerId, final Long memberId) {
    friendRepository.unfollow(followerId, memberId);
  }

  private void checkAlreadyFriend(final Long followerId, final Long memberId) {
    if (friendRepository.existsByFollowerIdAndFolloweeId(followerId, memberId)) {
      throw new BusinessException(ErrorCode.ENTITY_CONFLICT);
    }
  }

  private Friend checkIsFollower(final Long followerId, final Long memberId) {
    Friend friend = friendRepository.findByFollowerIdAndFolloweeId(followerId, memberId)
        .orElseThrow(
            () -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE));

    return friend;
  }

  @Override
  @Transactional
  public void enableNotification(Long followerId, Long memberId) {
    Friend follower = checkIsFollower(followerId, memberId);
    follower.enableNotification();
  }

  @Override
  @Transactional
  public void disableNotification(Long followerId, Long memberId) {
    Friend follower = checkIsFollower(followerId, memberId);
    follower.disableNotification();
  }
}
