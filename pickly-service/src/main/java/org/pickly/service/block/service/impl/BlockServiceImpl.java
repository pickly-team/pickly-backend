package org.pickly.service.block.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.block.entity.Block;
import org.pickly.service.block.repository.interfaces.BlockQueryRepository;
import org.pickly.service.block.repository.interfaces.BlockRepository;
import org.pickly.service.block.service.BlockService;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockServiceImpl implements BlockService {

  private final MemberService memberService;

  private final BookmarkService bookmarkService;

  private final BlockRepository blockRepository;

  private final BlockQueryRepository blockQueryRepository;

  @Override
  @Transactional
  public void blockMember(Long blockerId, Long blockeeId) {
    checkAlreadyBlock(blockerId, blockeeId);

    Member blocker = memberService.findById(blockerId);
    Member blockee = memberService.findById(blockeeId);

    blockRepository.save(new Block(blocker, blockee));
  }

  @Override
  @Transactional
  public void unBlockMember(Long blockerId, Long blockeeId) {
    Member blocker = memberService.findById(blockerId);
    Member blockee = memberService.findById(blockeeId);

    blockRepository.unBlockMember(blocker.getId(), blockee.getId());
  }

  @Override
  public List<Long> getBlockeeIdsByBlocker(final Long blockerId) {
    return blockRepository.getBlockeeIdsByBlocker(blockerId);
  }

  @Override
  public List<BlockMemberDTO> getBlockedMembers(Long blockerId, Long cursorId, Integer size) {
    return blockQueryRepository.getBlockedMembers(blockerId, cursorId, size);
  }


  @Override
  @Transactional
  public void blockBookmark(Long blockerId, Long bookmarkId) {
    checkAlreadyBlockBookmark(blockerId, bookmarkId);

    Member blocker = memberService.findById(blockerId);
    Bookmark bookmark = bookmarkService.findById(bookmarkId);

    blockRepository.save(new Block(blocker, bookmark));
  }

  @Override
  @Transactional
  public void unBlockBookmark(Long blockerId, Long bookmarkId) {
    blockRepository.unBlockBookmark(blockerId, bookmarkId);
  }

  @Override
  public List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, Long cursorId, Integer size) {
    return blockQueryRepository.getBlockedBookmarks(blockerId, cursorId, size);
  }

  private void checkAlreadyBlock(Long blockerId, Long blockeeId) {
    if (blockRepository.existsByBlockerIdAndBlockeeId(blockerId, blockeeId)) {
      throw new BusinessException(ErrorCode.ENTITY_CONFLICT);
    }
  }

  private void checkAlreadyBlockBookmark(Long blockerId, Long bookmarkId) {
    if (blockRepository.existsByBlockerIdAndBookmarkId(blockerId, bookmarkId)) {
      throw new BusinessException(ErrorCode.ENTITY_CONFLICT);
    }
  }
}
