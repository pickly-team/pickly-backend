package org.pickly.service.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "타 유저의 상세 페이지 조회에 사용되는 response")
public class MemberInfoRes {

  @Schema(description = "member ID (PK)", example = "1")
  private Long id;

  @Schema(description = "member nickname", example = "jojoldu")
  private String nickname;

  @Schema(description = "member profile emoji", example = "👨🏻‍💻")
  private String profileEmoji;

  @Schema(description = "is login member's followee?", example = "true")
  private Boolean isFollowing;

}
