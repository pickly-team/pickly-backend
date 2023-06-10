package org.pickly.service.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkInfoDTO {

  private String url;
  private String title;
  private String previewImageUrl;

  public BookmarkInfoDTO(String url) {
    this.url = url;
    this.title = null;
    this.previewImageUrl = null;
  }

  public void updateTitleAndImage(String title, String previewImageUrl) {
    this.title = title;
    this.previewImageUrl = previewImageUrl;
  }

}
