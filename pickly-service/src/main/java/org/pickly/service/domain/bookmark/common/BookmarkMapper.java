package org.pickly.service.domain.bookmark.common;

import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.dto.controller.request.BookmarkUpdateReq;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkRes;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.domain.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

  public BookmarkRes entityToResponseDto(Bookmark bookmark) {
    Category category = bookmark.getCategory();
    String timezone = bookmark.getMember().getTimezone();
    return BookmarkRes
        .builder()
        .id(bookmark.getId())
        .categoryId(category.getId())
        .categoryName(category.getName())
        .categoryEmoji(category.getEmoji())
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

  public BookmarkUpdateReqDTO toUpdateDto(BookmarkUpdateReq request) {
    return BookmarkUpdateReqDTO
        .builder()
        .categoryId(request.getCategoryId())
        .title(request.getTitle())
        .readByUser(request.getReadByUser())
        .visibility(request.getVisibility())
        .build();
  }

}
