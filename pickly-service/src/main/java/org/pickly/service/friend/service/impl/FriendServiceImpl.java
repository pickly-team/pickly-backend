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

  private final FriendRepository friendRepository;
  private final MemberService memberService;

  @Override
  @Transactional
  public void follow(final Long followerId, final Long memberId) {
    checkAlreadyFriend(followerId, memberId);
    Member follower = memberService.findById(followerId);
    Member followee = memberService.findById(memberId);
    friendRepository.save(new Friend(followee, follower));
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

}
