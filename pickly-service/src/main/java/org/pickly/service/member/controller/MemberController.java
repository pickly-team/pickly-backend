package org.pickly.service.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Member", description = "Member API")
public class MemberController {

  private final MemberService memberService;
  private final MemberMapper memberMapper;

  @PutMapping("/me")
  @Operation(summary = "Update my profile")
  public void updateMyProfile(
      // TODO: Replace with member ID from JWT or that from any other authentication method
      @RequestParam(value = "member_id")
      @Schema(description = "Member ID (should be replaced later on)", example = "1")
      Long memberId,

      @RequestBody
      MemberProfileUpdateReq request
  ) {
    memberService.updateMyProfile(memberId, memberMapper.toDTO(request));
  }

  @GetMapping("/{memberId}")
  @Operation(summary = "Get member profile")
  public MemberProfileRes getMemberProfile(
      @PathVariable
      @Schema(description = "Member ID", example = "1")
      Long memberId
  ) {
    return memberMapper.toResponse(
        memberService.findProfileByMemberId(memberId)
    );
  }
}
