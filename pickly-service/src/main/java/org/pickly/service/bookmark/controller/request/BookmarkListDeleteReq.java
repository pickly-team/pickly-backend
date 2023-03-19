package org.pickly.service.bookmark.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Schema(description = "Bookmark list delete request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NotNull(message = "북마크 ID 리스트는 필수입니다.")
@Size(min = 1, message = "북마크 ID 리스트는 최소 1개 이상이어야 합니다.")
public class BookmarkListDeleteReq {

  private List<Long> bookmarkIds;
}
