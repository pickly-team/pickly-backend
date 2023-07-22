package org.pickly.service.member.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchMemberResultResDTO {

  private Long memberId;
  private String nickname;
  private Boolean isFollowing;
  private String emoji;

  public SearchMemberResultResDTO(Long memberId, String nickname, String emoji) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.emoji = emoji;
  }

  public void setFollowingFlag(Boolean isFollowing) {
    this.isFollowing = isFollowing;
  }
}
