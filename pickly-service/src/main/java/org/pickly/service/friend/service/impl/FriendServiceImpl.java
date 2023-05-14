package org.pickly.service.friend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.block.service.BlockService;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.common.FriendMapper;
import org.pickly.service.friend.entity.Friend;
import org.pickly.service.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.FollowingResDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendServiceImpl implements FriendService {

  private static final int LAST_ITEM = 1;
  static final boolean FRIEND_NOTIFICATION_ON = true;
  private final BlockService blockService;
  private final FriendRepository friendRepository;
  private final FriendQueryRepository friendQueryRepository;
  private final MemberService memberService;
  private final FriendMapper friendMapper;

  @Override
  @Transactional
  public void follow(final Long followerId, final Long memberId) {
    checkAlreadyFriend(followerId, memberId);
    Member follower = memberService.findById(followerId);
    Member followee = memberService.findById(memberId);
    friendRepository.save(new Friend(followee, follower, FRIEND_NOTIFICATION_ON));
  }

  @Override
  @Transactional
  public void unfollow(final Long followerId, final Long memberId) {
    friendRepository.unfollow(followerId, memberId);
  }

  @Override
  public Long countFollowerByMember(Long memberId) {
    memberService.existsById(memberId);
    return friendRepository.countByFolloweeId(memberId);
  }

  @Override
  public Long countFollowingByMember(Long memberId) {
    memberService.existsById(memberId);
    return friendRepository.countByFollowerId(memberId);
  }

  private void checkAlreadyFriend(final Long followerId, final Long memberId) {
    if (friendRepository.existsByFollowerIdAndFolloweeId(followerId, memberId)) {
      throw new BusinessException(ErrorCode.ENTITY_CONFLICT);
    }
  }

  private Friend findFollowerById(final Long followerId, final Long memberId) {
    Friend friend = friendRepository.findByFollowerIdAndFolloweeId(followerId, memberId)
        .orElseThrow(
            () -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE));

    return friend;
  }

  @Override
  @Transactional
  public FriendNotificationStatusResDTO setNotification(Long followerId,
                                                        FriendNotificationStatusReqDTO request) {
    Friend friend = findFollowerById(followerId, request.getMemberId());
    friend.updateNotificationEnabled(request.getIsFollowing());
    return friendMapper.toFriendStatusResDTO(friend.getNotificationMode());
  }

  @Override
  public List<FollowerResDTO> findAllFollowerByMember(final Long memberId, final PageRequest pageRequest) {
    memberService.existsById(memberId);
    List<FollowerResDTO> followerResDtoList = friendQueryRepository.findAllFollowerByMember(memberId, pageRequest);
//    followerResDtoList = removeElement(followerResDtoList, followerResDtoList.size(), pageRequest.getPageSize());
    return removeBlockFollower(memberId, followerResDtoList);
  }

  @Override
  public List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest) {
    memberService.existsById(memberId);
    return friendQueryRepository.findAllFollowingByMember(memberId, pageRequest);
  }

  private List<FollowerResDTO> removeElement(final List<FollowerResDTO> itemList, final int size, final int pageSize) {
    if (size - LAST_ITEM >= pageSize) {
      List<FollowerResDTO> resultList = new ArrayList<>(itemList);
      resultList.remove(size - LAST_ITEM);
      return resultList;
    }
    return itemList;
  }

  private List<FollowerResDTO> removeBlockFollower(final Long memberId, final List<FollowerResDTO> followerResDtoList) {
    List<Long> blockList = blockService.getBlockeeIdsByBlocker(memberId);
    return followerResDtoList.stream()
        .filter(follower -> !blockList.contains(follower.getMemberId()))
        .toList();
  }
}
