package org.pickly.service.bookmark.common;

import org.pickly.service.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteReq;
import org.pickly.service.bookmark.controller.response.BookmarkRes;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteDTO;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

  public BookmarkListDeleteDTO toDTO(BookmarkListDeleteReq request) {
    return new BookmarkListDeleteDTO(request.getBookmarkIds());
  }

  public BookmarkRes entityToResponseDto(Bookmark request) {

    return BookmarkRes
        .builder()
        .id(request.getId())
        .categoryId(request.getCategory().getId())
        .memberId(request.getMember().getId())
        .url(request.getUrl())
        .title(request.getTitle())
        .previewImageUrl(request.getPreviewImageUrl())
        .isUserLike(request.getIsUserLike())
        .readByUser(request.getReadByUser())
        .visibility(request.getVisibility().getDescription())
        .build();
  }
}
