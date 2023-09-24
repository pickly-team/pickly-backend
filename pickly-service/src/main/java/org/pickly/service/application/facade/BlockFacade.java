package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.block.service.BlockWriteService;
import org.pickly.service.domain.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.domain.friend.service.interfaces.FriendService;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockFacade {

  private final MemberService memberService;
  private final BookmarkService bookmarkService;
  private final BlockWriteService blockWriteService;
  private final FriendService friendService;

  public void blockMember(Long fromMemberId, Long toMemberId) {
    var fromMember = memberService.findById(fromMemberId);
    var toMember = memberService.findById(toMemberId);

    blockWriteService.blockMember(fromMember, toMember);

    friendService.unfollow(fromMemberId, toMemberId);
    friendService.unfollow(toMemberId, fromMemberId);
  }

  public void unblockMember(Long fromMemberId, Long toMemberId) {
    var fromMember = memberService.findById(fromMemberId);
    var toMember = memberService.findById(toMemberId);

    blockWriteService.unblockMember(fromMember, toMember);
  }

  public void blockBookmark(Long fromMemberId, Long toBookmarkId) {
    var fromMember = memberService.findById(fromMemberId);
    var toBookmark = bookmarkService.findById(toBookmarkId);

    blockWriteService.blockBookmark(fromMember, toBookmark);

    bookmarkService.cancelLikeBookmark(toBookmarkId);
  }

  public void unblockBookmark(Long fromMemberId, Long toBookmarkId) {
    var fromMember = memberService.findById(fromMemberId);
    var toBookmark = bookmarkService.findById(toBookmarkId);

    blockWriteService.unblockBookmark(fromMember, toBookmark);
  }

}
