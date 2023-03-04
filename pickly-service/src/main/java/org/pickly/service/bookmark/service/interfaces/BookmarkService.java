package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;

public interface BookmarkService {

  Bookmark findById(Long id);

  void likeBookmark(Long memberId);

  void cancelLikeBookmark(Long memberId);

  Long countMemberLikes(Long memberId);

  PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(PageRequest pageRequest, Long memberId);

  PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(PageRequest pageRequest, Long memberId,
      Long categoryId, Boolean readByUser);

}
