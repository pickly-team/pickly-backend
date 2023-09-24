package org.pickly.service.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.friend.entity.Friend;
import org.pickly.service.domain.friend.exception.FriendException;
import org.pickly.service.domain.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.domain.friend.repository.interfaces.FriendRepository;
import org.pickly.service.domain.friend.service.dto.FollowerResDTO;
import org.pickly.service.domain.friend.service.dto.FollowingResDTO;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendReadService {

  private final FriendRepository friendRepository;
  private final FriendQueryRepository friendQueryRepository;
  private final MemberService memberService;

  public Long countFollowerByMember(Long memberId) {
    memberService.existsById(memberId);
    return friendRepository.countByFolloweeId(memberId);
  }

  public Long countFollowingByMember(Long memberId) {
    memberService.existsById(memberId);
    return friendRepository.countByFollowerId(memberId);
  }

  public void checkAlreadyFriend(final Long followerId, final Long memberId) {
    if (friendRepository.existsByFollowerIdAndFolloweeId(followerId, memberId)) {
      throw new FriendException.AlreadyFriendException();
    }
  }

  public Friend findFollowerById(final Long followerId, final Long memberId) {
    return friendRepository.findByFollowerIdAndFolloweeId(followerId, memberId)
        .orElseThrow(FriendException.FriendNotFoundException::new);
  }

  public List<FollowerResDTO> findAllFollowerByMember(final Long memberId, final PageRequest pageRequest) {
    memberService.existsById(memberId);
    return friendQueryRepository.findAllFollowerWithOutBlockByMember(memberId, pageRequest);
  }

  public List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest) {
    memberService.existsById(memberId);
    return friendQueryRepository.findAllFollowingByMember(memberId, pageRequest);
  }

}
