package org.pickly.service.block.service.impl;

import java.util.List;
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
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.service.interfaces.FriendService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockServiceImpl implements BlockService {

  private final MemberService memberService;

  private final BookmarkService bookmarkService;

  private final FriendService friendService;

  private final BlockRepository blockRepository;

  private final BlockQueryRepository blockQueryRepository;

  @Override
  @Transactional
  public void blockMember(Long blockerId, Long blockeeId) {
    checkAlreadyBlock(blockerId, blockeeId);

    Member blocker = memberService.findById(blockerId);
    Member blockee = memberService.findById(blockeeId);

    blockRepository.save(new Block(blocker, blockee));

    friendService.unfollow(blockerId, blockeeId);
    friendService.unfollow(blockeeId, blockerId);
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
  public List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest) {
    return blockQueryRepository.getBlockedMembers(blockerId, pageRequest);
  }


  @Override
  @Transactional
  public void blockBookmark(Long blockerId, Long bookmarkId) {
    checkAlreadyBlockBookmark(blockerId, bookmarkId);

    Member blocker = memberService.findById(blockerId);
    Bookmark bookmark = bookmarkService.findById(bookmarkId);

    blockRepository.save(new Block(blocker, bookmark));
    bookmarkService.cancelLikeBookmark(bookmarkId);
  }

  @Override
  @Transactional
  public void unBlockBookmark(Long blockerId, Long bookmarkId) {
    blockRepository.unBlockBookmark(blockerId, bookmarkId);
  }

  @Override
  public List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest) {
    return blockQueryRepository.getBlockedBookmarks(blockerId, pageRequest);
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
