package org.pickly.service.friend.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingRes {

  private Long memberId;

  private String loginId;

}