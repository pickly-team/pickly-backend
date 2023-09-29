package org.pickly.service.domain.friend.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingRes {

  private Long memberId;

  private String nickname;

  private String emoji;

}
