package org.pickly.service.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberInfoRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Tag(name = "Member", description = "Member API")
public class MemberController {

  private final MemberService memberService;
  private final MemberMapper memberMapper;

  @PutMapping("/me")
  @Operation(summary = "Update my profile")
  public void updateMyProfile(
      // TODO: Replace with member ID from JWT or that from any other authentication method
      @RequestParam
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID (should be replaced later on)", example = "1")
      Long memberId,

      @RequestBody
      @Valid
      MemberProfileUpdateReq request
  ) {
    memberService.updateMyProfile(memberId, memberMapper.toDTO(request));
  }

  @GetMapping("/{memberId}")
  @Operation(summary = "Get member profile")
  public MemberProfileRes getMemberProfile(
      @PathVariable
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId
  ) {
    return memberMapper.toResponse(
        memberService.findProfileByMemberId(memberId)
    );
  }

  // TODO: loginId는 JWT로 대체 예정
  @GetMapping("/{memberId}/info")
  @Operation(summary = "특정 유저의 상세 정보 조회")
  public MemberInfoRes getMemberInfo(
      @Parameter(name = "memberId", description = "조회할 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    return memberMapper.toMemberInfoRes(
        memberService.findInfoByMemberId(memberId, loginId)
    );
  }

}
