package org.pickly.service.block.service;

import java.util.List;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.springframework.data.domain.Pageable;

public interface BlockService {

  void blockMember(Long blockerId, Long blockeeId);

  void unBlockMember(Long blockerId, Long blockeeId);

  List<BlockMemberDTO> getBlockedMembers(Long blockerId, Pageable page);

  void blockBookmark(Long blockerId, Long bookmarkId);

  void unBlockBookmark(Long blockerId, Long bookmarkId);

  List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, Pageable page);
}
