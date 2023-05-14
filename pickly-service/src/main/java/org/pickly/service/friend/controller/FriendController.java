package org.pickly.service.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.common.FriendMapper;
import org.pickly.service.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.friend.controller.response.FriendNotificationStatusRes;
import org.pickly.service.friend.controller.response.MemberFollowerInfoRes;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.friend.service.dto.MemberFollowerInfoResDTO;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendController {

  private final FriendService friendService;
  private final FriendMapper friendMapper;

  // TODO: JWT 개발 후 followerId를 삭제 예정
  @PostMapping("/members/{followerId}/following/{memberId}")
  @Operation(summary = "특정 멤버 팔로우")
  public void follow(
      @Parameter(name = "followerId", description = "팔로우 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long followerId,

      @Parameter(name = "memberId", description = "팔로우 할 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    friendService.follow(followerId, memberId);
  }

  // TODO: JWT 개발 후 followerId를 삭제 예정
  @DeleteMapping("/members/{followerId}/following/{memberId}")
  @Operation(summary = "특정 멤버 언팔로우")
  public void unfollow(
      @Parameter(name = "followerId", description = "언팔로우 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long followerId,

      @Parameter(name = "memberId", description = "언팔로우 할 유저 ID 값", example = "3", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    friendService.unfollow(followerId, memberId);
  }

  // TODO: JWT 개발 후 followerId를 삭제 예정
  @PutMapping("/members/{followerId}/following/{memberId}/notification")
  @Operation(summary = "특정 멤버 알림 설정 및 해제")
  public FriendNotificationStatusRes switchNotification(
      @Parameter(name = "followerId", description = "알림을 받고 싶은 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long followerId,

      @Parameter(name = "memberId", description = "알림을 받을 유저 ID 값", example = "2", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @RequestBody
      @Valid final
      FriendNotificationStatusReq request
  ) {
    FriendNotificationStatusResDTO friendStatusResDTO = friendService.setNotification(followerId,
        friendMapper.toDTO(memberId, request));
    return friendMapper.toFriendStatusDTO(
        friendStatusResDTO.getNotificationMode(friendStatusResDTO.getIsNotificationAllowed()));

  }

  @Operation(summary = "특정 유저의 팔로워 정보 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  @GetMapping("/members/{memberId}/followers")
  public MemberFollowerInfoRes findAllFollowerByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    MemberFollowerInfoResDTO resDto = friendService.findAllFollowerByMember(memberId);
    return friendMapper.toMemberFollowerInfoRes(resDto);
  }

}
