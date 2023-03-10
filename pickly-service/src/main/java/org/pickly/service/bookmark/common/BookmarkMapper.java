package org.pickly.service.bookmark.common;

import org.pickly.service.bookmark.controller.request.BookmarkListDeleteReq;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteDTO;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

  public BookmarkListDeleteDTO toDTO(BookmarkListDeleteReq request) {
    return new BookmarkListDeleteDTO(request.getBookmarkIds());
  }
}
