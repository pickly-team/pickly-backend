package org.pickly.service.domain.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberProfileDTO {

  private Long id;
  private String name;
  private String nickname;
  private String profileEmoji;
  private Boolean isFollowing;
  private Boolean isBlocked;
}