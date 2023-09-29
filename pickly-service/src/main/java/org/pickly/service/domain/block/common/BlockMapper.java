package org.pickly.service.domain.block.common;

import org.pickly.service.domain.block.dto.controller.response.BlockBookmarkRes;
import org.pickly.service.domain.block.dto.controller.response.BlockMemberRes;
import org.pickly.service.domain.block.dto.service.BlockBookmarkDTO;
import org.pickly.service.domain.block.dto.service.BlockMemberDTO;
import org.springframework.stereotype.Component;

@Component
public class BlockMapper {

  public static BlockMemberRes toMember(BlockMemberDTO blockMemberDTO) {
    return new BlockMemberRes(blockMemberDTO.getId(), blockMemberDTO.getNickname(),
        blockMemberDTO.getProfileEmoji());
  }

  public static BlockBookmarkRes toBookmark(BlockBookmarkDTO blockBookmarkDTO) {

    return new BlockBookmarkRes(blockBookmarkDTO.getId(), blockBookmarkDTO.getTitle(),
        blockBookmarkDTO.getPreviewImageUrl());
  }
}
