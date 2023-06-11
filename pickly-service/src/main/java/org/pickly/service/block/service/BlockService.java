package org.pickly.service.block.service;

import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;

import java.util.List;

public interface BlockService {

  void blockMember(Long blockerId, Long blockeeId);

  void unBlockMember(Long blockerId, Long blockeeId);

  List<Long> getBlockeeIdsByBlocker(Long blockerId);

  List<BlockMemberDTO> getBlockedMembers(Long blockerId, Long cursorId, Integer size);

  void blockBookmark(Long blockerId, Long bookmarkId);

  void unBlockBookmark(Long blockerId, Long bookmarkId);

  List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, Long cursorId, Integer size);
}
