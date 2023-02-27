package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Member profile update request")
public class MemberProfileUpdateReq {

  private String name;
  private String nickname;
  private String profileEmoji;
}
