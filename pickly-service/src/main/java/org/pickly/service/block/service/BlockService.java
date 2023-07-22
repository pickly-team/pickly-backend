package org.pickly.service.block.service;

import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;

import java.util.List;
import org.pickly.service.common.utils.page.PageRequest;

public interface BlockService {

  void blockMember(Long blockerId, Long blockeeId);

  void unBlockMember(Long blockerId, Long blockeeId);

  List<Long> getBlockeeIdsByBlocker(Long blockerId);

  List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest);

  void blockBookmark(Long blockerId, Long bookmarkId);

  void unBlockBookmark(Long blockerId, Long bookmarkId);

  List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest);
}
