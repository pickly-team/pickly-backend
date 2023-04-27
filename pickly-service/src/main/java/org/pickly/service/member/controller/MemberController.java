package org.pickly.service.member.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.request.MemberStatusReq;
import org.pickly.service.member.controller.response.HardModeRes;
import org.pickly.service.member.controller.response.MemberModeRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MyProfileRes;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  // TODO: ApiResponse.content 없어도 Swagger Schema 동작하는지 확인 필요
  @GetMapping("/me")
  @Operation(summary = "Get member profile")
  public MyProfileRes getMemberProfile(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    return memberMapper.toResponse(
        memberService.findMyProfile(loginId)
    );
  }

  @GetMapping("/{memberId}")
  @Operation(summary = "Get member profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = {
              @Content(schema = @Schema(implementation = MemberProfileRes.class))
          })
  })
  public MemberProfileRes getMemberProfile(
      @PathVariable
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId,

      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    return memberMapper.toResponse(
        memberService.findProfileById(loginId, memberId)
    );
  }

  @GetMapping("/{memberId}/mode")
  @Operation(summary = "Get member mode info")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = {
              @Content(schema = @Schema(implementation = MemberModeRes.class))
          })
  })
  public MemberModeRes getMemberMode(
      @PathVariable @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId
  ) {
    return memberMapper.toResponse(
        memberService.findModeByMemberId(memberId)
    );
  }

  @PutMapping("/status")
  @Operation(summary = "HardMode Status ON / OFF")
  public HardModeRes switchToHardMode(
      @RequestParam
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId,

      @RequestBody
      @Valid
      MemberStatusReq request
  ) {
    return memberMapper.toMemberStatusRes(
        memberService.setHardMode(memberId, memberMapper.toStatusDTO(request)));
  }

  @DeleteMapping("/me")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete member")
  public void deleteMember(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    memberService.deleteMember(loginId);
  }
}
