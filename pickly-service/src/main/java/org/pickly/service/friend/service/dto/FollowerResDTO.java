package org.pickly.service.friend.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.friend.entity.Friend;

@Getter
@Builder
public class FollowerResDTO {

  private Long memberId;

  private String loginId;

  private Boolean isFollowing;

  @QueryProjection
  public FollowerResDTO(Long followeeId, Friend follower) {
    this.memberId = follower.getFollowee().getId();
    this.loginId = follower.getFollowee().getUsername();
    this.isFollowing = followeeId.equals(follower.getFollower().getId());
  }

}
