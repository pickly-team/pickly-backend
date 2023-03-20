package org.pickly.service.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.common.FriendMapper;
import org.pickly.service.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.friend.controller.request.FriendNotificationStatusRes;
import org.pickly.service.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
