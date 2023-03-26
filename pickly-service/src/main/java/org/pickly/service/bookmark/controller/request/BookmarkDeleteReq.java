package org.pickly.service.bookmark.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Schema(description = "Bookmark delete request")
@NoArgsConstructor
@NotNull(message = "북마크 ID는 필수입니다.")
public class BookmarkDeleteReq {

  private Long bookmarkId;
}
