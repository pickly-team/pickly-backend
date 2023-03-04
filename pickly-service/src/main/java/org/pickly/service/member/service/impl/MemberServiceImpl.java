package org.pickly.service.member.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.Password;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  public void existsById(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw new EntityNotFoundException("존재하지 않는 유저입니다.");
    }
  }

  public Member findById(Long id) {
    return memberRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 member 입니다."));
  }

}
