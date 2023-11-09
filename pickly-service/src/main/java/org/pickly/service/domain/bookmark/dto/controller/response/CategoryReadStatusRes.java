package org.pickly.service.domain.bookmark.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;

@Getter
@AllArgsConstructor
public class CategoryReadStatusRes {

  private long categoryId;
  private String categoryName;
  private String categoryEmoji;
  private BookmarkReadStatus readStatus;

  public CategoryReadStatusRes(final Category category, final BookmarkReadStatus status) {
    this.categoryId = category.getId();
    this.categoryName = category.getName();
    this.categoryEmoji = category.getEmoji();
    this.readStatus = status;
  }

}
