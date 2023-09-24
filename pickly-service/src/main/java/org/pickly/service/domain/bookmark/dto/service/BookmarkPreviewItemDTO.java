package org.pickly.service.domain.bookmark.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.domain.bookmark.entity.Bookmark;

@Getter
@Builder
@AllArgsConstructor
public class BookmarkPreviewItemDTO {

  @Schema(description = "북마크의 ID (PK)", example = "1")
  private Long bookmarkId;

  @Schema(description = "북마크 제목", example = "Git 쌈싸먹기")
  private String title;

  @Schema(description = "북마크 원본 URL", example = "https://naver.com/1242")
  private String url;

  @Schema(description = "북마크 미리보기 이미지 URL", example = "https://naver.com/image/1242")
  private String previewImageUrl;

  @Schema(description = "유저가 좋아요 한 북마크인지?", example = "false")
  private Boolean isUserLike;

  @Schema(description = "유저가 읽은 북마크인지?", example = "false")
  private Boolean readByUser;

  @Schema(description = "북마크에 달린 댓글 수", example = "5")
  private Long commentCnt;

  @Schema(description = "북마크 생성 일자", example = "2023-01-25")
  private LocalDate createdDate;

  private BookmarkPreviewItemDTO() {

  }

  public static BookmarkPreviewItemDTO from(final Bookmark bookmark, final Long commentCnt) {
    return BookmarkPreviewItemDTO.builder()
        .bookmarkId(bookmark.getId())
        .title(bookmark.getTitle())
        .url(bookmark.getUrl())
        .previewImageUrl(bookmark.getPreviewImageUrl())
        .isUserLike(bookmark.getIsUserLike())
        .readByUser(bookmark.getReadByUser())
        .commentCnt(commentCnt)
        .createdDate(bookmark.getCreatedAt().toLocalDate())
        .build();
  }

}
