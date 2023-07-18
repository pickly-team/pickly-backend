package org.pickly.service.friend.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.pickly.service.friend.entity.Friend;
import org.pickly.service.member.entity.Member;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class FollowerResDTO {

  private Long memberId;

  private String loginId;

  private Boolean isFollowing;

  private String emoji;

  @QueryProjection
  public FollowerResDTO(Long followeeId, Friend follower, Member followerInfo) {
    this.memberId = followerInfo.getId();
    this.loginId = followerInfo.getUsername();
    this.isFollowing = (follower == null) ? false : followeeId.equals(follower.getFollower().getId());
    this.emoji = followerInfo.getProfileEmoji();
  }

}
