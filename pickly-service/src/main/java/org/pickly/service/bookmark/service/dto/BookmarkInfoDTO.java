package org.pickly.service.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pickly.service.common.utils.timezone.TimezoneHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    this.title = title.isBlank() ? makeTitle() : title;
    this.previewImageUrl = title.isBlank() ? null : previewImageUrl;
  }

  private String makeTitle() {
    LocalDateTime now = TimezoneHandler.getNowByZone();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분의 북마크");
    return now.format(formatter);
  }

}
