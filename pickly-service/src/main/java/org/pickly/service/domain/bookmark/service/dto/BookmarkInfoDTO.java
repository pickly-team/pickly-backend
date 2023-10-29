package org.pickly.service.domain.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.timezone.TimezoneHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
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

  public BookmarkInfoDTO(String url, String title, String previewImageUrl, String timezone) {
    this.url = url;
    this.title = (title == null || title.isBlank()) ? makeTitle(timezone) : title;
    this.previewImageUrl = previewImageUrl.isBlank() ? null : previewImageUrl;
  }

  public void updateTitleAndImage(String title, String previewImageUrl, String timezone) {
    this.title = title.isBlank() ? makeTitle(timezone) : title;
    this.previewImageUrl = previewImageUrl.isBlank() ? null : previewImageUrl;
  }

  private String makeTitle(String timezone) {
    LocalDateTime now = TimezoneHandler.getNowInTimezone(timezone);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분의 북마크");
    return now.format(formatter);
  }

}
