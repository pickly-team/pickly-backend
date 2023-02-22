package org.pickly.service.member.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.Password;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  // FIXME: graphql 예제 코드입니다! 추후 삭제 예정
  @Override
  public List<Member> getMembers() {
    return memberRepository.findAll();
  }

  // FIXME: graphql 예제 코드입니다! 추후 삭제 예정22
  @Override
  @Transactional
  public Member save(final String username) {
    Member member = Member.builder()
        .username(username)
        .password(new Password(username))
        .isHardMode(false)
        .email("eunjiShin@gmail.com")
        .name("은지상")
        .nickname("graphQL-test")
        .build();
    return memberRepository.save(member);
  }
}
