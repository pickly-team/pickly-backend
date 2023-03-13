package org.pickly.service.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.dto.controller.CategoryCreateReq;
import org.pickly.service.category.dto.controller.CategoryUpdateReq;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

  private final CategoryService categoryService;

  @Operation(summary = "카테고리 생성 요청")
  @PostMapping("/members/{memberId}/categories")
  public void createCategory(
      @PathVariable
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId,

      @Valid @RequestBody
      @Schema(description = "Category Name", example = "내일 안으로 꼭 봐야할 자바 면접 질문")
      CategoryCreateReq request
  ) {
    categoryService.create(memberId, request);
  }

  @Operation(summary = "카테고리 수정 요청")
  @PutMapping("/members/{memberId}/categories/{categoryId}")
  public void updateCategory(
      @PathVariable
      @Positive(message = "유저 ID는 양수입니다.")
      @Schema(description = "Member ID", example = "1")
      Long memberId,

      @PathVariable
      @Positive(message = "카테고리 ID는 양수입니다.")
      @Schema(description = "Category ID", example = "1")
      Long categoryId,

      @Valid @RequestBody
      CategoryUpdateReq request
  ) {
    categoryService.update(memberId, categoryId, request);
  }
}
