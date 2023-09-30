package org.pickly.service.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.friend.dto.service.FollowerResDTO;
import org.pickly.service.domain.friend.dto.service.FollowingResDTO;
import org.pickly.service.domain.friend.entity.Friend;
import org.pickly.service.domain.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.domain.friend.repository.interfaces.FriendRepository;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.pickly.service.domain.friend.exception.FriendException.AlreadyFriendException;
import static org.pickly.service.domain.friend.exception.FriendException.FriendNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendReadService {

  private final FriendRepository friendRepository;
  private final FriendQueryRepository friendQueryRepository;
  private final MemberReadService memberReadService;

  public Long countFollowerByMember(Long memberId) {
    memberReadService.existsById(memberId);
    return friendRepository.countByFolloweeId(memberId);
  }

  public Long countFollowingByMember(Long memberId) {
    memberReadService.existsById(memberId);
    return friendRepository.countByFollowerId(memberId);
  }

  public void checkAlreadyFriend(final Long followerId, final Long memberId) {
    if (friendRepository.existsByFollowerIdAndFolloweeId(followerId, memberId)) {
      throw new AlreadyFriendException();
    }
  }

  public boolean checkUsersAreFriend(final Long loginId, final Long memberId) {
    return
        friendRepository.existsByFollowerIdAndFolloweeId(loginId, memberId)
        || friendRepository.existsByFollowerIdAndFolloweeId(memberId, loginId);
  }

  public Friend findFollowerById(final Long followerId, final Long memberId) {
    return friendRepository.findByFollowerIdAndFolloweeId(followerId, memberId)
        .orElseThrow(FriendNotFoundException::new);
  }

  public List<FollowerResDTO> findAllFollowerByMember(final Long memberId, final PageRequest pageRequest) {
    memberReadService.existsById(memberId);
    return friendQueryRepository.findAllFollowerWithOutBlockByMember(memberId, pageRequest);
  }

  public List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest) {
    memberReadService.existsById(memberId);
    return friendQueryRepository.findAllFollowingByMember(memberId, pageRequest);
  }

  public Long getFollowerCount(Long followeeId) {
    return friendRepository.countByFolloweeId(followeeId);
  }

  public Long getFolloweeCount(Long followerId) {
    return friendRepository.countByFollowerId(followerId);
  }

  public boolean existsByFollowerIdAndFolloweeId(Long fromMemberId, Long toMemberId) {
    return friendRepository.existsByFollowerIdAndFolloweeId(fromMemberId, toMemberId);
  }

}
