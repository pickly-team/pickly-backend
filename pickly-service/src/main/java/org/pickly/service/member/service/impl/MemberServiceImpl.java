package org.pickly.service.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
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
  private final MemberMapper memberMapper;

  @Override
  @Transactional
  public void updateMyProfile(Long memberId, MemberProfileUpdateDTO request) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

    member.updateProfile(
        request.getName(),
        request.getNickname(),
        request.getProfileEmoji()
    );
  }

  @Override
  @Transactional(readOnly = true)
  public MemberProfileDTO findProfileByNickname(String nickname) {
    Member member = memberRepository.findOneByNickname(nickname)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

    return memberMapper.toMemberProfileDTO(member);
  }

  @Override
  @Transactional
  public void register(MemberRegisterDto request){
    Member member = Member.builder()
        .username(request.getUsername())
        .isHardMode(request.getIsHardMode())
        .email(request.getEmail())
        .name(request.getName())
        .nickname(request.getNickname())
        .build();

    memberRepository.save(member);
  }
}
