package org.pickly.service.member.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

  private final MemberService memberService;

  @QueryMapping
  public List<Member> getMembers() {
    return memberService.getMembers();
  }

}
