package org.pickly.service.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkListDeleteResDTO {

  private Boolean isDeleted;

  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted != 0;
  }

}
