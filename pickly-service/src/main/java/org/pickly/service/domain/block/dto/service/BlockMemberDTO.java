package org.pickly.service.domain.block.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockMemberDTO {

  private Long id;
  private String nickname;
  private String profileEmoji;

}
