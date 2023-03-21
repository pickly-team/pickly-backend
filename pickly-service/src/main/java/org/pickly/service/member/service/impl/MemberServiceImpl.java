package org.pickly.service.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final NotificationStandardService notificationStandardService;
  private final MemberRepository memberRepository;
  private final FriendRepository friendRepository;
  private final MemberMapper memberMapper;

  @Override
  public void existsById(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw new EntityNotFoundException("존재하지 않는 유저입니다.");
    }
  }

  @Transactional
  public void updateMyProfile(Long memberId, MemberProfileUpdateDTO request) {
    Member member = findById(memberId);

    member.updateProfile(
        request.getName(),
        request.getNickname(),
        request.getProfileEmoji()
    );
  }

  @Override
  @Transactional(readOnly = true)
  public MemberProfileDTO findProfileByMemberId(final Long memberId, final Long loginId) {
    Member member = findById(memberId);
    Boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(loginId, memberId);
    return memberMapper.toMemberProfileDTO(member, isFollowing);
  }

  @Override
  public MemberModeDTO findModeByMemberId(final Long memberId) {
    Member member = findById(memberId);
    NotificationStandard standard = notificationStandardService.findByMember(memberId);
    return memberMapper.toMemberModeDTO(member, standard);
  }

  @Override
  public Member findById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 member 입니다."));
  }

}
