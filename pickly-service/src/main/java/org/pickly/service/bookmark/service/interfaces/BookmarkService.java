package org.pickly.service.bookmark.service.interfaces;

import java.util.List;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;

public interface BookmarkService {

  Bookmark findById(Long id);

  Bookmark findByIdWithCategory(Long id);

  void likeBookmark(Long memberId);

  void cancelLikeBookmark(Long memberId);

  Long countMemberLikes(Long memberId);

  PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(PageRequest pageRequest, Long memberId);

  PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(PageRequest pageRequest, Long memberId,
      Long categoryId, Boolean readByUser, Visibility visibility);

  BookmarkDeleteResDTO deleteBookmark(Long bookmarkId);

  BookmarkListDeleteResDTO deleteBookmarks(List<Long> bookmarkIds);

}
