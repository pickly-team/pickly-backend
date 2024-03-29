package org.pickly.service.domain.friend.dto.service;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.friend.entity.Friend;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FollowerResDTO {

  private Long memberId;

  private String nickname;

  private Boolean isFollowing;

  private String emoji;

  @QueryProjection
  public FollowerResDTO(Long followeeId, Friend follower, Member followerInfo) {
    this.memberId = followerInfo.getId();
    this.nickname = followerInfo.getNickname();
    this.isFollowing = (follower == null) ? false : followeeId.equals(follower.getFollower().getId());
    this.emoji = followerInfo.getProfileEmoji();
  }

}
