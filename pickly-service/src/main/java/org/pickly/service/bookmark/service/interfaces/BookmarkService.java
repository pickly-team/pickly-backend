package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.dto.controller.MemberBookmarkRes;
import org.pickly.service.bookmark.dto.controller.MemberLikeBookmarkRes;

public interface BookmarkService {

  Long countMemberLikes(Long memberId);

  MemberLikeBookmarkRes findMemberLikeBookmarks(Long cursorId, Long memberId, Integer pageSize);

  MemberBookmarkRes findMemberBookmarks(Long cursorId, Long memberId, Long categoryId, Boolean isUserRead, Integer pageSize);

}
