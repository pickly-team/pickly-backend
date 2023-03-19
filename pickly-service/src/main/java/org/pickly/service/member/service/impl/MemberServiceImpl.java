package org.pickly.service.member.service.impl;

import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.common.utils.base.AuthTokenUtil;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final FriendRepository friendRepository;
  private final MemberMapper memberMapper;
  private final AuthTokenUtil authTokenUtil;

  @Override
  public void existsById(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw new EntityNotFoundException("존재하지 않는 유저입니다.");
    }
  }

  @Override
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
  @Transactional
  public MemberRegisterDto register(String token) {
    FirebaseToken decodedToken = authTokenUtil.getDecodedToken(token);
    Member member = memberMapper.tokenToMember(decodedToken);
    memberRepository.save(member);
    return memberMapper.toMemberRegisterDTO(member);
  }

  @Override
  @Transactional(readOnly = true)
  public MemberProfileDTO findProfileByMemberId(final Long memberId, final Long loginId) {
    Member member = findById(memberId);
    Boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(loginId, memberId);
    return memberMapper.toMemberProfileDTO(member, isFollowing);
  }

  @Override
  public Member findById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 member 입니다."));
  }

}
