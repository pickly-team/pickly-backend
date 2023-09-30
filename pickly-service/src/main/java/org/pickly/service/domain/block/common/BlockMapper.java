package org.pickly.service.domain.block.common;

import org.pickly.service.domain.block.dto.controller.response.BlockBookmarkRes;
import org.pickly.service.domain.block.dto.controller.response.BlockMemberRes;
import org.pickly.service.domain.block.dto.service.BlockBookmarkDTO;
import org.pickly.service.domain.block.dto.service.BlockMemberDTO;
import org.springframework.stereotype.Component;

@Component
public class BlockMapper {

  public static BlockMemberRes toMember(BlockMemberDTO dto) {
    return new BlockMemberRes(
        dto.getId(), dto.getNickname(), dto.getProfileEmoji()
    );
  }

  public static BlockBookmarkRes toBookmark(BlockBookmarkDTO dto) {

    return new BlockBookmarkRes(
        dto.getId(), dto.getTitle(), dto.getPreviewImageUrl()
    );
  }

}
