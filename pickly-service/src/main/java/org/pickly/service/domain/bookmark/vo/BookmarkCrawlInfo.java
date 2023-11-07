package org.pickly.service.domain.bookmark.vo;

import lombok.Getter;

@Getter
public class BookmarkCrawlInfo {

  private final String title;
  private final String thumbnail;

  public BookmarkCrawlInfo(String url, String title, String thumbnail) {
    this.title = (title.isBlank()) ? url : title;
    this.thumbnail = thumbnail;
  }

}
