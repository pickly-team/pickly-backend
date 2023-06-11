package org.pickly.service.category.controller.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOrderNumUpdateReq {

  @Positive(message = "카테고리 ID (PK)는 양수입니다.")
  private Long categoryId;

  @Positive(message = "카테고리 순서는 양수입니다.")
  private Integer orderNum;

}
