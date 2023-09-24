package org.pickly.service.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Member profile response")
public class MyProfileRes {

  @Schema(description = "member ID (PK)", example = "1")
  private Long id;

  @Schema(description = "member name", example = "John Doe")
  private String name;

  @Schema(description = "member nickname", example = "johndoe")
  private String nickname;

  @Schema(description = "member profile emoji", example = "👨🏻‍💻")
  private String profileEmoji;
  @Schema(description = "number of followers", example = "10")
  private Long followersCount;

  @Schema(description = "number of followees", example = "10")
  private Long followeesCount;

  @Schema(description = "number of bookmarks", example = "10")
  private Long bookmarksCount;
}
