package org.pickly.service.domain.block.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockMemberRes {

  private Long id;
  private String nickname;
  private String profileEmoji;

}
