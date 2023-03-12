package org.pickly.service.member.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.base.RequestUtil;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MemberRegisterRes;
import org.pickly.service.member.service.dto.MemberRegisterDto;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "Member", description = "Member API")
public class MemberController {

  private final MemberService memberService;
  private final MemberMapper memberMapper;
  private final FirebaseAuth firebaseAuth;

  @PutMapping("/me")
  @Operation(summary = "Update my profile")
  public ResponseEntity<Void> updateMyProfile(
      // TODO: Replace with member ID from JWT or that from any other authentication method
      @RequestParam(value = "member_id")
      @Schema(description = "Member ID (should be replaced later on)", example = "1")
      Long memberId,

      @RequestBody
      MemberProfileUpdateReq request
  ) {
    memberService.updateMyProfile(memberId, memberMapper.toDTO(request));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{nickname}")
  @Operation(summary = "Get member profile")
  public ResponseEntity<MemberProfileRes> getMemberProfile(
      @PathVariable
      @Schema(description = "Member nickname", example = "pickly")
      String nickname
  ) {
    MemberProfileRes response = memberMapper.toResponse(
        memberService.findProfileByNickname(nickname)
    );
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<MemberRegisterRes> register(@RequestHeader("Authorization") String authorization){
    FirebaseToken decodedToken;

    try{
      String token = RequestUtil.getAuthorizationToken(authorization);
      decodedToken = firebaseAuth.verifyIdToken(token);
    }catch (IllegalArgumentException | FirebaseAuthException e){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
    }

    MemberRegisterDto memberRegisterDto = memberMapper.toMemberRegisterDTO(decodedToken.getUid(), false,
        decodedToken.getEmail(), decodedToken.getName(), "");

    memberService.register(memberRegisterDto);

    MemberRegisterRes response = memberMapper.toMemberRegisterResponse(memberRegisterDto);

    return ResponseEntity.ok(response);
  }
}
