package org.pickly.service.member.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

  private final MemberService memberService;

  // FIXME: graphql 예제 코드입니다! 추후 삭제 예정
  @QueryMapping
  public List<Member> getMembers() {
    return memberService.getMembers();
  }

  // FIXME: graphql 예제 코드입니다! 추후 삭제 예정22
  @MutationMapping
  public Member save(@Argument String username) {
    return memberService.save(username);
  }

}
