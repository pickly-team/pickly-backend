package org.pickly.service.domain.block.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.block.dto.service.BlockBookmarkDTO;
import org.pickly.service.domain.block.dto.service.BlockMemberDTO;
import org.pickly.service.domain.block.repository.interfaces.BlockQueryRepository;
import org.pickly.service.domain.block.repository.interfaces.BlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.pickly.service.domain.block.exception.BlockException.AlreadyBlockException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockReadService {

  private final BlockRepository blockRepository;

  private final BlockQueryRepository blockQueryRepository;

  public List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest) {
    return blockQueryRepository.getBlockedMembers(blockerId, pageRequest);
  }

  public List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest) {
    return blockQueryRepository.getBlockedBookmarks(blockerId, pageRequest);
  }

  public void checkAlreadyBlock(Long blockerId, Long blockeeId) {
    if (blockRepository.existsByBlockerIdAndBlockeeId(blockerId, blockeeId)) {
      throw new AlreadyBlockException();
    }
  }

  public void checkAlreadyBlockBookmark(Long blockerId, Long bookmarkId) {
    if (blockRepository.existsByBlockerIdAndBookmarkId(blockerId, bookmarkId)) {
      throw new AlreadyBlockException();
    }
  }

  public boolean existsByBlockerIdAndBlockeeId(Long fromMemberId, Long toMemberId) {
    return blockRepository.existsByBlockerIdAndBlockeeId(fromMemberId, toMemberId);
  }

}
