package org.pickly.service.category.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryRes {

  private long categoryId;
  private String name;
  private String emoji;
  private Integer orderNum;

}
