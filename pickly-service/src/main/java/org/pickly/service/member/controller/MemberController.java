package org.pickly.service.member.controller;

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
import org.pickly.service.common.utils.base.RequestUtil;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.request.MemberStatusReq;
import org.pickly.service.member.controller.response.*;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Validated
@Tag(name = "Member", description = "Member API")
public class MemberController {

  private final MemberService memberService;
  private final MemberMapper memberMapper;

  @PutMapping("/me")
  @Operation(summary = "유저 프로필을 수정한다.")
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
  @Operation(summary = "내 유저 프로필을 조회한다.")
  public MyProfileRes getMemberProfile(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    return memberMapper.toResponse(
        memberService.findMyProfile(loginId)
    );
  }

  @GetMapping("/{memberId}")
  @Operation(summary = "유저 프로필을 ID로 조회한다.")
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
  @Operation(summary = "유저의 모드를 조회한다.")
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
  @Operation(summary = "하드모드 상태를 켜거나 끈다.")
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
  @Operation(summary = "유저를 탈퇴한다.")
  public void deleteMember(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    memberService.deleteMember(loginId);
  }

  @PostMapping("/register")
  @Operation(summary = "회원가입")
  public ResponseEntity<MemberRegisterRes> register(
      @RequestHeader("Authorization") String authorization
  ) {
    String token = RequestUtil.getAuthorizationToken(authorization);
    MemberRegisterDto memberRegisterDto = memberService.register(token);
    MemberRegisterRes response = memberMapper.toMemberRegisterResponse(memberRegisterDto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/id")
  @Operation(summary = "Access token으로 유저 ID를 조회한다.")
  public ResponseEntity<Long> getMemberId(
      @RequestHeader("Authorization") String authorization) {
    String token = RequestUtil.getAuthorizationToken(authorization);
    MemberProfileDTO memberProfileDto = memberService.getMemberIdByToken(token);
    return ResponseEntity.ok(memberProfileDto.getId());
  }

  @GetMapping("/{memberId}/search/{keyword}")
  @Operation(
      summary = "검색어를 통해 특정 유저를 검색한다.",
      description = "검색의 연관된 초성, 혹은 일치하는 항목들에 대해 유저 검색결과 프로필 리스트를 반환한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public PageResponse<SearchMemberResultRes> searchMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "keyword", description = "검색어", example = "ww0077", required = true)
      @PathVariable final String keyword,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "3")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    List<SearchMemberResultRes> resDto = memberService.searchMemberByKeywords(keyword, memberId,
            pageRequest)
        .stream().map(memberMapper::toSearchMemberResultRes).toList();

    PageResponse<SearchMemberResultRes> response = new PageResponse<>(resDto.size(),
        pageRequest.getPageSize(), resDto);
    response.removeElement(pageRequest.getPageSize());

    return response;
  }
}
