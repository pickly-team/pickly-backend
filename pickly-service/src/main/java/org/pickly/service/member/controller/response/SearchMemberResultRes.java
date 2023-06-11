package org.pickly.service.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchMemberResultRes {

  private Long memberId;
  private String nickname;
  private String profileImageUrl;
  private Boolean isFollowing;
}
