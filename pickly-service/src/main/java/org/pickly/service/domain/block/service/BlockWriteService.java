package org.pickly.service.domain.block.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.block.entity.Block;
import org.pickly.service.domain.block.repository.interfaces.BlockRepository;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockWriteService {

  private final BlockReadService blockReadService;
  private final BlockRepository blockRepository;

  public void blockMember(Member fromMember, Member toMember) {
    blockReadService.checkAlreadyBlock(toMember.getId(), fromMember.getId());
    blockRepository.save(new Block(fromMember, toMember));
  }

  public void unblockMember(Member fromMember, Member toMember) {
    blockRepository.unBlockMember(fromMember.getId(), toMember.getId());
  }

  public void blockBookmark(Member fromMember, Bookmark toBookmark) {
    blockReadService.checkAlreadyBlockBookmark(fromMember.getId(), toBookmark.getId());
    blockRepository.save(new Block(fromMember, toBookmark));
  }

  public void unblockBookmark(Member fromMember, Bookmark toBookmark) {
    blockRepository.unBlockBookmark(fromMember.getId(), toBookmark.getId());
  }

}
