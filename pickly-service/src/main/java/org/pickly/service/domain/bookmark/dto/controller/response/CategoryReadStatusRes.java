package org.pickly.service.domain.bookmark.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;

@Getter
@AllArgsConstructor
public class CategoryReadStatusRes {

  private long categoryId;
  private BookmarkReadStatus readStatus;

}
