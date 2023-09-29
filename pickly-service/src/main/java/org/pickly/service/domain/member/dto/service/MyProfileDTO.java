package org.pickly.service.domain.member.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyProfileDTO {

  private Long id;
  private String name;
  private String nickname;
  private String profileEmoji;
  private Long followersCount;
  private Long followeesCount;
  private Long bookmarksCount;

}
