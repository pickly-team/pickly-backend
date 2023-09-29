package org.pickly.service.domain.friend.dto.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowerRes {

  private Long memberId;

  private String nickname;

  private Boolean isFollowing;

  private String emoji;

}
