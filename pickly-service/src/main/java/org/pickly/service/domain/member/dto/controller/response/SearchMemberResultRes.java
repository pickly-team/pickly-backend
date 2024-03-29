package org.pickly.service.domain.member.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchMemberResultRes {

  private Long memberId;
  private String nickname;
  private Boolean isFollowing;
  private String emoji;
  private Boolean isBlocked;

}
