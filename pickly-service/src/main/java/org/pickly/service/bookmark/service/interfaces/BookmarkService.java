package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;

public interface BookmarkService {

  Long countMemberLikes(Long memberId);

  PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(PageRequest pageRequest, Long memberId);

  PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(PageRequest pageRequest, Long memberId, Long categoryId, Boolean isUserRead);

}
