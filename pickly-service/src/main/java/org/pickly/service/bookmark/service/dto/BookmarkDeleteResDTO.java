package org.pickly.service.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDeleteResDTO {

  private Boolean isDeleted;

  public void setIsDeleted() {
    this.isDeleted = true;
  }

}
