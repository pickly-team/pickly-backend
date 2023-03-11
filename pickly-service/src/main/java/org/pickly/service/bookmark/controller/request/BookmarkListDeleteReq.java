package org.pickly.service.bookmark.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Schema(description = "Bookmark list delete request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkListDeleteReq {

  private List<Long> bookmarkIds;
}
