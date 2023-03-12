package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Member Mode Statsu update request")
public class MemberStatusReq {

  private Boolean isHardMode;

}
