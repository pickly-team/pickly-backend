package org.pickly.service.block.common;

import java.util.List;
import java.util.stream.Collectors;
import org.pickly.service.block.controller.response.BlockBookmarkRes;
import org.pickly.service.block.controller.response.BlockMemberRes;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
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
