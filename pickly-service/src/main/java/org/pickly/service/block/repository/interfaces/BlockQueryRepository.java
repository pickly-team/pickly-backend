package org.pickly.service.block.repository.interfaces;

import java.util.List;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.pickly.service.common.utils.page.PageRequest;
import org.springframework.data.domain.Pageable;

public interface BlockQueryRepository {

  List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest);

  List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest);

}
