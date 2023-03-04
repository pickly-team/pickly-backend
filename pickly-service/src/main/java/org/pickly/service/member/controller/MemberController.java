package org.pickly.service.member.controller;

import lombok.RequiredArgsConstructor;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

  private final MemberService memberService;

}
