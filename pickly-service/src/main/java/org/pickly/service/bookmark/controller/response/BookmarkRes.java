package org.pickly.service.bookmark.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class BookmarkRes {

  @NonNull
  private Long id;

  @NonNull
  private Long categoryId;

  @NonNull
  private Long memberId;

  private String url;

  private String title;

  private String previewImageUrl;

  private Boolean isUserLike;

  private Boolean readByUser;

  private String visibility;
}

