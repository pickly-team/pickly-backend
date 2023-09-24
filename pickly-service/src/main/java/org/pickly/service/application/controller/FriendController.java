package org.pickly.service.application.controller;

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
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.domain.friend.controller.request.FriendNotificationStatusReq;
import org.pickly.service.domain.friend.controller.response.FollowerRes;
import org.pickly.service.domain.friend.controller.response.FollowingRes;
import org.pickly.service.domain.friend.controller.response.FriendNotificationStatusRes;
import org.pickly.service.domain.friend.service.dto.FriendNotificationStatusResDTO;
import org.pickly.service.domain.friend.service.interfaces.FriendService;
import org.pickly.service.domain.friend.common.FriendMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Friend", description = "친구 API")
public class FriendController {

  private final FriendService friendService;
  private final FriendMapper friendMapper;

  // TODO: JWT 개발 후 followerId를 삭제 예정
  @PostMapping("/members/{followerId}/following/{memberId}")
  @Operation(summary = "특정 유저를 팔로우한다.")
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
  @Operation(summary = "특정 유저를 언팔로우한다.")
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
  @Operation(summary = "특정 유저에 대한 알림을 설정 또는 해제한다.")
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

  @GetMapping("/members/{memberId}/followers/count")
  @Operation(summary = "특정 유저의 팔로워 수를 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public long countFollowerByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return friendService.countFollowerByMember(memberId);
  }

  @GetMapping("/members/{memberId}/followers")
  @Operation(
      summary = "특정 유저의 팔로워 정보를 조회한다.",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 마지막 요소의 loginId"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public PageResponse<FollowerRes> findAllFollowerByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "ww0077")
      @RequestParam(required = false) final String cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    List<FollowerRes> resDto = friendService.findAllFollowerByMember(memberId, pageRequest)
        .stream().map(friendMapper::toFollowerRes).toList();
    PageResponse<FollowerRes> response = new PageResponse<>(resDto.size(),
        pageRequest.getPageSize(), resDto);
    response.removeElement(pageRequest.getPageSize());
    return response;
  }

  @GetMapping("/members/{memberId}/followees/count")
  @Operation(summary = "특정 유저의 팔로잉 수를 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public long countFollowingByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return friendService.countFollowingByMember(memberId);
  }

  @GetMapping("/members/{memberId}/followings")
  @Operation(
      summary = "특정 유저의 팔로잉 정보를 조회한다.",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 마지막 요소의 loginId"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public PageResponse<FollowingRes> findAllFollowingByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "ww0077")
      @RequestParam(required = false) final String cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    List<FollowingRes> resDto = friendService.findAllFollowingByMember(memberId, pageRequest)
        .stream().map(friendMapper::toFollowingRes).toList();
    PageResponse<FollowingRes> response = new PageResponse<>(resDto.size(),
        pageRequest.getPageSize(), resDto);
    response.removeElement(pageRequest.getPageSize());
    return response;
  }

}
