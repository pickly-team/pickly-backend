package org.pickly.service.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MyProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberStatusDTO;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.service.interfaces.NotificationStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final NotificationStandardService notificationStandardService;
  private final MemberRepository memberRepository;
  private final FriendRepository friendRepository;
  private final MemberMapper memberMapper;
  private final BookmarkRepository bookmarkRepository;

  @Override
  public void existsById(Long memberId) {
    if (!memberRepository.existsByIdAndDeletedAtIsNull(memberId)) {
      throw new EntityNotFoundException("존재하지 않는 유저입니다.");
    }
  }

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
  public MyProfileDTO findMyProfile(final Long memberId) {
    Member member = findById(memberId);
    Long followersCount = friendRepository.countByFolloweeId(memberId);
    Long followeesCount = friendRepository.countByFollowerId(memberId);
    Long bookmarksCount = bookmarkRepository.countByMemberId(memberId);
    return memberMapper.toMyProfileDTO(member, followersCount, followeesCount, bookmarksCount);
  }

  @Override
  @Transactional(readOnly = true)
  public MemberProfileDTO findProfileById(final Long loginId, final Long memberId) {
    boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(loginId, memberId);
    Member member = findById(memberId);
    return memberMapper.toMemberProfileDTO(member, isFollowing);
  }

  @Override
  @Transactional
  public MemberModeDTO setHardMode(Long memberId, MemberStatusDTO request) {
    Member member = findById(memberId);

    member.setHardMode(request.getIsHardMode());
    return memberMapper.toMemberStatusDTO(member.isHardMode(member.getIsHardMode()));
    
  public MemberModeDTO findModeByMemberId(final Long memberId) {
    Member member = findById(memberId);
    NotificationStandard standard = notificationStandardService.findByMember(memberId);
    return memberMapper.toMemberModeDTO(member, standard);
  }

  @Override
  public Member findById(Long id) {
    return memberRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 member 입니다."));
  }

  @Override
  public void deleteMember(Long memberId) {
    Member member = findById(memberId);
    member.delete();
  }
}
