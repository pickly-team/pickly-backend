package org.pickly.service.friend.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowerRes {

  private Long memberId;

  private String loginId;

  private Boolean isFollowing;

  private String emoji;

}
