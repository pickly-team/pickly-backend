package org.pickly.service.common.utils.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "페이지네이션을 위한 Request")
public class PageRequest {

  @Schema(description = "커서 ID :: 직전 response의 마지막 요소의 ID", example = "5", defaultValue = "0")
  private Long cursorId;

  @Schema(description = "한 페이지에 출력할 아이템 수", example = "10", defaultValue = "15")
  private Integer pageSize;

  public PageRequest(Long cursorId, Integer pageSize) {
    this.cursorId = cursorId == null ? 0L : cursorId;
    this.pageSize = pageSize == null ? 15 : pageSize;
  }

}
