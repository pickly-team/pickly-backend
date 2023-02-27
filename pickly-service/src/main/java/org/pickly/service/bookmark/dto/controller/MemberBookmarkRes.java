package org.pickly.service.bookmark.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberBookmarkRes {

  @Schema(description = "다음 페이지가 남았는지?", example = "true")
  private boolean hasNext;

  private MemberBookmarkRes() {

  }

}
