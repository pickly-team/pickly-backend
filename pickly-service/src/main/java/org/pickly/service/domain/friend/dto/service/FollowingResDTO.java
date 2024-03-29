package org.pickly.service.domain.friend.dto.service;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.pickly.service.domain.member.entity.Member;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FollowingResDTO {

  private Long memberId;

  private String nickname;

  private String emoji;

  @QueryProjection
  public FollowingResDTO(Member followerInfo) {
    this.memberId = followerInfo.getId();
    this.nickname = followerInfo.getNickname();
    this.emoji = followerInfo.getProfileEmoji();
  }

}
