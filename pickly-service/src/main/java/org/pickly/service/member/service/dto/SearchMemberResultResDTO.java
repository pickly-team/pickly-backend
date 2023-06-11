package org.pickly.service.member.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchMemberResultResDTO {

  private Long memberId;
  private String nickname;
  private String profileImageUrl;
  private Boolean isFollowing;

  public SearchMemberResultResDTO(Long memberId, String nickname, String profileImageUrl) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }

  public void setFollowingFlag(Boolean isFollowing) {
    this.isFollowing = isFollowing;
  }
}
