package org.pickly.service.category.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Category update request")
public class CategoryUpdateReq {
  @NotNull
  @Schema(description = "자동 삭제 기능 여부", example = "true")
  private Boolean isAutoDeleteMode;

  @NotBlank(message = "카테고리 이름을 입력해주세요.")
  @Schema(description = "category name", example = "카테고리 이름")
  private String categoryName;

  @NotBlank(message = "카테고리 이모지를 입력해주세요.")
  @Schema(description = "category emoji", example = "📚")
  private String emoji;
}