package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.block.service.BlockWriteService;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.bookmark.service.BookmarkWriteService;
import org.pickly.service.domain.friend.service.FriendWriteService;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockFacade {

  private final MemberService memberService;
  private final BookmarkReadService bookmarkReadService;
  private final BookmarkWriteService bookmarkWriteService;
  private final BlockWriteService blockWriteService;
  private final FriendWriteService friendWriteService;

  public void blockMember(Long fromMemberId, Long toMemberId) {
    var fromMember = memberService.findById(fromMemberId);
    var toMember = memberService.findById(toMemberId);

    blockWriteService.blockMember(fromMember, toMember);

    friendWriteService.unfollow(fromMemberId, toMemberId);
    friendWriteService.unfollow(toMemberId, fromMemberId);
  }

  public void unblockMember(Long fromMemberId, Long toMemberId) {
    var fromMember = memberService.findById(fromMemberId);
    var toMember = memberService.findById(toMemberId);

    blockWriteService.unblockMember(fromMember, toMember);
  }

  public void blockBookmark(Long fromMemberId, Long toBookmarkId) {
    var fromMember = memberService.findById(fromMemberId);
    var toBookmark = bookmarkReadService.findById(toBookmarkId);

    blockWriteService.blockBookmark(fromMember, toBookmark);

    bookmarkWriteService.cancelLike(toBookmarkId);
  }

  public void unblockBookmark(Long fromMemberId, Long toBookmarkId) {
    var fromMember = memberService.findById(fromMemberId);
    var toBookmark = bookmarkReadService.findById(toBookmarkId);

    blockWriteService.unblockBookmark(fromMember, toBookmark);
  }

}
