package org.pickly.service.domain.bookmark.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.domain.bookmark.entity.Bookmark;

@Getter
@Builder
@AllArgsConstructor
public class BookmarkItemDTO {

  @Schema(description = "북마크의 ID (PK)", example = "1")
  private Long bookmarkId;

  @Schema(description = "북마크 제목", example = "Git 쌈싸먹기")
  private String title;

  @Schema(description = "북마크 원본 URL", example = "https://naver.com/1242")
  private String url;

  @Schema(description = "소속 카테고리 이름", example = "백엔드")
  private String categoryName;

  @Schema(description = "소속 카테고리 이모지", example = "🤩")
  private String categoryEmoji;

  @Schema(description = "유저가 좋아요 한 북마크인지?", example = "false")
  private Boolean isUserLike;

  private BookmarkItemDTO() {

  }

  public static BookmarkItemDTO from(final Bookmark bookmark) {
    return BookmarkItemDTO.builder()
        .bookmarkId(bookmark.getId())
        .title(bookmark.getTitle())
        .url(bookmark.getUrl())
        .categoryName(bookmark.getCategory().getName())
        .categoryEmoji(bookmark.getCategory().getEmoji())
        .isUserLike(bookmark.getIsUserLike())
        .build();
  }

}
