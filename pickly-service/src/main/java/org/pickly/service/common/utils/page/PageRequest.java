package org.pickly.service.common.utils.page;

import lombok.Getter;

@Getter
public class PageRequest {

  private Long cursorId;
  private Integer pageSize;

  public PageRequest(Long cursorId, Integer pageSize) {
    this.cursorId = cursorId == null ? 0L : cursorId;
    this.pageSize = pageSize == null ? 15 : pageSize;
  }

}
