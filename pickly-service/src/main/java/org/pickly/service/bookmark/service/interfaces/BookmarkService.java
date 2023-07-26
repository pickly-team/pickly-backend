package org.pickly.service.bookmark.service.interfaces;

import org.pickly.service.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.entity.Member;

import java.util.List;
import java.util.Map;

public interface BookmarkService {

  Bookmark findById(Long id);

  Bookmark findByIdAndRead(Long id, Long memberId);

  Bookmark findByIdWithCategory(Long id);

  String getTitleFromUrl(Long memberId, String url);

  void likeBookmark(Long bookmarkId);

  void cancelLikeBookmark(Long bookmarkId);

  Long countMemberLikes(Long memberId);

  PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(PageRequest pageRequest, Long memberId);

  PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(PageRequest pageRequest, Long memberId,
      Long categoryId, Boolean readByUser, Visibility visibility);

  BookmarkDeleteResDTO deleteBookmark(Long bookmarkId);

  BookmarkListDeleteResDTO deleteBookmarks(List<Long> bookmarkIds);

  Bookmark create(BookmarkCreateReq dto);

  PageResponse<BookmarkItemDTO> findBookmarkByCategoryId(PageRequest pageRequest, Long categoryId);

  void updateBookmark(Long bookmarkId, BookmarkUpdateReqDTO request);

  Map<Member, List<Bookmark>> findAllUnreadBookmark();

}
