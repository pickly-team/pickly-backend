package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.dto.controller.MemberLikeBookmarkRes;

public interface BookmarkService {

  Long countMemberLikes(Long memberId);

  MemberLikeBookmarkRes findMemberLikeBookmarks(Long cursorId, Long memberId, Integer pageSize);

}
