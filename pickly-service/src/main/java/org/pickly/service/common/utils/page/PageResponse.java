package org.pickly.service.common.utils.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

  @Schema(description = "다음 페이지가 남았는지?", example = "true")
  private boolean hasNext;

  @Schema(description = "현재 페이지의 아이템 리스트")
  private List<T> contents;

  public static boolean makeHasNext(final int contentSize, final int pageSize) {
    return contentSize > pageSize;
  }

  public PageResponse(final int contentSize, final int pageSize, final List<T> contents) {
    this.hasNext = makeHasNext(contentSize, pageSize);
    this.contents = contents;
  }

}

