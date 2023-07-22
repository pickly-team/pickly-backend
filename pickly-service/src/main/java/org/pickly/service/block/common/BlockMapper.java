package org.pickly.service.block.common;

import java.util.List;
import java.util.stream.Collectors;
import org.pickly.service.block.controller.response.BlockBookmarkRes;
import org.pickly.service.block.controller.response.BlockBookmarkRes.BlockBookmark;
import org.pickly.service.block.controller.response.BlockMemberRes;
import org.pickly.service.block.controller.response.BlockMemberRes.BlockMember;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.springframework.stereotype.Component;

@Component
public class BlockMapper {

  public static BlockMemberRes toMember(List<BlockMemberDTO> blockMemberDTO) {
    List<BlockMember> blockMemberList = blockMemberDTO.stream().map(blockMember ->
            BlockMember.of(blockMember.getId(), blockMember.getNickname(),
                blockMember.getProfileEmoji()))
        .collect(Collectors.toList());

    return BlockMemberRes.of(blockMemberList);
  }

  public static BlockBookmarkRes toBookmark(List<BlockBookmarkDTO> blockBookmarkDTO) {
    List<BlockBookmark> blockBookmarkList = blockBookmarkDTO.stream().map(blockBookmark ->
            BlockBookmark.of(blockBookmark.getId(), blockBookmark.getTitle(),
                blockBookmark.getPreviewImageUrl()))
        .collect(Collectors.toList());

    return BlockBookmarkRes.of(blockBookmarkList);
  }
}
