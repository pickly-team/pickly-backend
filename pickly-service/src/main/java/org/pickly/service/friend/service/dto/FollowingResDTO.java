package org.pickly.service.friend.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.pickly.service.member.entity.Member;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FollowingResDTO {

  private Long memberId;

  private String loginId;

  private String emoji;

  @QueryProjection
  public FollowingResDTO(Member followerInfo) {
    this.memberId = followerInfo.getId();
    this.loginId = followerInfo.getUsername();
    this.emoji = followerInfo.getProfileEmoji();
  }

}
