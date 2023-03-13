package org.pickly.service.category.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Category create request")
public class CategoryCreateReq {
  @NotBlank(message = "카테고리 이름을 입력해주세요.")
  private String categoryName;
}
