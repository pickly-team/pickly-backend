package org.pickly.service.bookmark.common;

import org.pickly.service.bookmark.controller.response.BookmarkDeleteRes;
import org.pickly.service.bookmark.controller.response.BookmarkListDeleteRes;
import org.pickly.service.bookmark.controller.request.BookmarkUpdateReq;
import org.pickly.service.bookmark.controller.response.BookmarkRes;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

  public BookmarkDeleteRes toBookmarkDelete(Boolean request) {
    return new BookmarkDeleteRes(request);
  }

  public BookmarkListDeleteRes toBookmarkListDelete(Boolean request) {
    return new BookmarkListDeleteRes(request);
  }

  public BookmarkRes entityToResponseDto(Bookmark bookmark) {
    Category category = bookmark.getCategory();
    String timezone = bookmark.getMember().getTimezone();
    return BookmarkRes
        .builder()
        .id(bookmark.getId())
        .categoryId(category.getId())
        .categoryName(category.getName())
        .memberId(bookmark.getMember().getId())
        .url(bookmark.getUrl())
        .title(bookmark.getTitle())
        .previewImageUrl(bookmark.getPreviewImageUrl())
        .isUserLike(bookmark.getIsUserLike())
        .readByUser(bookmark.getReadByUser())
        .visibility(bookmark.getVisibility().getDescription())
        .createdAt(TimezoneHandler.convertToTimezone(bookmark.getCreatedAt(), timezone))
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
