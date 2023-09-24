package org.pickly.service.domain.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkListDeleteResDTO {

  private Boolean isDeleted;

  public void setIsDeleted() {
    this.isDeleted = true;
  }

}
