package org.pickly.service.domain.block.repository.interfaces;

import java.util.List;
import org.pickly.service.domain.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.domain.block.service.dto.BlockMemberDTO;
import org.pickly.service.common.utils.page.PageRequest;

public interface BlockQueryRepository {

  List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest);

  List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest);

}
