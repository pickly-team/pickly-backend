package org.pickly.service.domain.bookmark.service.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BookmarkInfoDTO {

  private String url;
  private String title;
  private String previewImageUrl;

  public BookmarkInfoDTO(String url, String title, String previewImageUrl) {
    this.url = url;
    this.title = (title == null || title.isBlank()) ? url : title;
    this.previewImageUrl = previewImageUrl.isBlank() ? null : previewImageUrl;
  }

}
