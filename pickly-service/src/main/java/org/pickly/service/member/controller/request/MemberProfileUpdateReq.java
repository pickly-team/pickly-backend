package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Member profile update request")
public class MemberProfileUpdateReq {

  @Schema(description = "member name", example = "John Doe")
  private String name;

  @Schema(description = "member nickname", example = "johndoe")
  private String nickname;

  @Schema(description = "member profile emoji", example = "👨🏻‍💻")
  private String profileEmoji;
}
