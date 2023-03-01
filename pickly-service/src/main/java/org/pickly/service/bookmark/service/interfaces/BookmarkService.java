package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.common.utils.page.PageResponse;

public interface BookmarkService {

  Long countMemberLikes(Long memberId);

  PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(Long cursorId, Long memberId, Integer pageSize);

  PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(Long cursorId, Long memberId, Long categoryId, Boolean isUserRead, Integer pageSize);

}
