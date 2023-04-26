package org.pickly.service.bookmark.common;

import org.pickly.service.bookmark.controller.request.BookmarkDeleteReq;
import org.pickly.service.bookmark.controller.request.BookmarkDeleteRes;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteReq;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteRes;
import org.pickly.service.bookmark.controller.request.BookmarkUpdateReq;
import org.pickly.service.bookmark.controller.response.BookmarkRes;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteReqDTO;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteReqDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

  public BookmarkDeleteRes toBookmarkDelete(Boolean request) {
    return new BookmarkDeleteRes(request);
  }

  public BookmarkListDeleteRes toBookmarkListDelete(Boolean request) {
    return new BookmarkListDeleteRes(request);
  }

  public BookmarkDeleteReqDTO toBookmarkDeleteReqDTO(BookmarkDeleteReq request) {
    return new BookmarkDeleteReqDTO(request.getBookmarkId());
  }

  public BookmarkDeleteResDTO toBookmarkDeleteResDTO(Boolean request) {
    return new BookmarkDeleteResDTO(request);
  }

  public BookmarkListDeleteReqDTO toBookmarkListDeleteReqDTO(BookmarkListDeleteReq request) {
    return new BookmarkListDeleteReqDTO(request.getBookmarkIds());
  }

  public BookmarkListDeleteResDTO toBookmarkListDeleteResDTO(Boolean request) {
    return new BookmarkListDeleteResDTO(request);
  }

  public BookmarkRes entityToResponseDto(Bookmark bookmark) {

    return BookmarkRes
        .builder()
        .id(bookmark.getId())
        .categoryId(bookmark.getCategory().getId())
        .memberId(bookmark.getMember().getId())
        .url(bookmark.getUrl())
        .title(bookmark.getTitle())
        .previewImageUrl(bookmark.getPreviewImageUrl())
        .isUserLike(bookmark.getIsUserLike())
        .readByUser(bookmark.getReadByUser())
        .visibility(bookmark.getVisibility().getDescription())
        .build();
  }

  public BookmarkUpdateReqDTO toBookmarkUpdateReqDTO(BookmarkUpdateReq request) {
    return BookmarkUpdateReqDTO
        .builder()
        .categoryId(request.getCategoryId())
        .title(request.getTitle())
        .readByUser(request.getReadByUser())
        .visibility(request.getVisibility())
        .build();
  }
}
