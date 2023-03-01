package org.pickly.service.common.utils.page;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PageResponse<T> {

  @Schema(description = "다음 페이지가 남았는지?", example = "true")
  private boolean nextPage;

  @Schema(description = "현재 페이지의 아이템 리스트")
  private List<T> contents;

}

