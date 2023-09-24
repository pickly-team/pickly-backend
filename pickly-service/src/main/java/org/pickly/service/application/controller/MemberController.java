package org.pickly.service.application.controller;

import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.MemberFacade;
import org.pickly.service.common.utils.base.AuthTokenUtil;
import org.pickly.service.common.utils.base.RequestUtil;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.domain.member.common.MemberMapper;
import org.pickly.service.domain.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.domain.member.controller.request.MemberStatusReq;
import org.pickly.service.domain.member.controller.request.NotificationSettingsUpdateReq;
import org.pickly.service.domain.member.controller.response.*;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.member.service.MemberWriteService;
import org.springframework.http.HttpStatus;
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

  private final MemberReadService memberReadService;
  private final MemberWriteService memberWriteService;
  private final MemberFacade memberFacade;
  private final MemberMapper memberMapper;
  private final AuthTokenUtil authTokenUtil;

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
    memberFacade.update(memberId, memberMapper.toDTO(request));
  }

  // TODO: ApiResponse.content 없어도 Swagger Schema 동작하는지 확인 필요
  @GetMapping("/me")
  @Operation(summary = "내 유저 프로필을 조회한다.")
  public MyProfileRes getMemberProfile(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    return memberMapper.toResponse(
        memberFacade.findMyProfile(loginId)
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
        memberFacade.findProfileById(loginId, memberId)
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
        memberFacade.findModeByMemberId(memberId)
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
    Member member = memberFacade.setHardMode(memberId, memberMapper.toStatusDTO(request));
    return memberMapper.toMemberStatusRes(member);
  }


  @PutMapping("/{memberId}/notification-settings")
  @Operation(summary = "특정 유저의 알림 설정을 변경한다.")
  public void updateNotificationSettings(
      @RequestParam
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId,

      @RequestBody
      @Valid
      NotificationSettingsUpdateReq request
  ) {
    memberFacade.updateNotificationSettings(
        memberId, request.getTimezone(), request.getFcmToken()
    );
  }

  @DeleteMapping("/me")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "유저를 탈퇴한다.")
  public void deleteMember(
      @Parameter(name = "loginId", description = "로그인 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId
  ) {
    memberFacade.delete(loginId);
  }

  @PostMapping("/register")
  @Operation(summary = "회원가입")
  @ResponseStatus(HttpStatus.OK)
  public MemberRegisterRes register(
      @RequestHeader("Authorization") String authorization
  ) {
    String token = RequestUtil.getAuthorizationToken(authorization);
    FirebaseToken decodedToken = authTokenUtil.validateToken(token);
    Member request = memberMapper.tokenToMember(decodedToken);

    Member member = memberFacade.create(request);
    return memberMapper.toResponse(member);
  }

  @GetMapping("/id")
  @Operation(summary = "Access token으로 유저 ID를 조회한다.")
  @ResponseStatus(HttpStatus.OK)
  public Long getMemberId(
      @RequestHeader("Authorization") String authorization
  ) {
    var token = RequestUtil.getAuthorizationToken(authorization);
    var decodedToken = authTokenUtil.validateToken(token);
    var member = memberReadService.findByEmail(decodedToken.getEmail());
    return member.getId();
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
    List<SearchMemberResultRes> resDto = memberReadService.searchMemberByKeywords(keyword, memberId,
            pageRequest)
        .stream().map(memberMapper::toSearchMemberResultRes).toList();

    PageResponse<SearchMemberResultRes> response = new PageResponse<>(resDto.size(),
        pageRequest.getPageSize(), resDto);
    response.removeElement(pageRequest.getPageSize());

    return response;
  }

  @PostMapping("/{memberId}/authentication-code")
  @Operation(
      summary = "크롬 익스텐션 대응 : 멤버 인증코드를 발급한다.",
      description = "웹 로그인을 할 때 사용할 본인 인증용 인증코드를 발급한다. (만료 기한 = 5분)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public String makeMemberAuthenticationCode(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return memberFacade.makeAuthenticationCode(memberId);
  }

  @DeleteMapping("/authentication-code")
  @Operation(
      summary = "크롬 익스텐션 대응 : 멤버 인증 코드를 확인한다.",
      description = "인증 코드를 제출하고, 맵핑된 멤버 ID를 받는다. 멤버 ID는 암호화한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "잘못된 인증 코드"),
  })
  public String checkMemberAuthenticationCode(
      @Parameter(name = "code", description = "발급 받은 인증 코드 값", example = "2319", required = true)
      @NotBlank(message = "인증 코드는 필수 값입니다.") @RequestParam final String code
  ) {
    return memberWriteService.checkMemberAuthenticationCode(code);
  }

}
