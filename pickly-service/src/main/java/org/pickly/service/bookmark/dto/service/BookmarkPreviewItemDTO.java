package org.pickly.service.bookmark.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
  private Boolean isUserRead;

  @Schema(description = "댓글이 있는 북마크인지?", example = "true")
  private Boolean hasComment;

  @Schema(description = "북마크 생성 일자", example = "2023-01-25")
  private LocalDate createdDate;

  private BookmarkPreviewItemDTO() {

  }

}
