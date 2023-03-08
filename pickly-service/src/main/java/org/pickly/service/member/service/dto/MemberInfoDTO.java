package org.pickly.service.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfoDTO {

  private Long id;
  private String nickname;
  private String profileEmoji;
  private Boolean isFollowing;

}
