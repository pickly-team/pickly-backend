package org.pickly.service.domain.bookmark.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookmarkRes {

  private Long id;

  private Long categoryId;

  private String categoryName;

  private String categoryEmoji;

  private Long memberId;

  private String url;

  private String title;

  private String previewImageUrl;

  private Boolean isUserLike;

  private Boolean readByUser;

  private String visibility;

  private LocalDateTime createdAt;

}

