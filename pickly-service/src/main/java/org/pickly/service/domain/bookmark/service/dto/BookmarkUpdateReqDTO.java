package org.pickly.service.domain.bookmark.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.pickly.service.domain.bookmark.entity.Visibility;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkUpdateReqDTO {

  @NonNull
  private Long categoryId;

  @NonNull
  private String title;

  @NonNull
  private Boolean readByUser;

  @NonNull
  private Visibility visibility;

}
