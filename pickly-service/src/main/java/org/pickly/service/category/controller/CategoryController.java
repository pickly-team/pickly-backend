package org.pickly.service.category.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.dto.controller.CategoryMapper;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryResponseDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.dto.service.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.common.utils.page.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping("/categories")
  public void create(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long memberId,

      @RequestBody @Valid final List<CategoryRequestDTO> requests
  ) {
    categoryService.create(memberId, requests);
  }

  @PutMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> update(
      @PathVariable @Positive Long categoryId,
      @RequestBody @Valid final CategoryUpdateRequestDTO dto
  ) {
    Category category = categoryService.update(categoryId, dto);

    return ResponseEntity
        .ok(CategoryMapper.toResponseDTO(category));
  }

  @DeleteMapping("/categories/{categoryId}")
  public ResponseEntity<Void> delete(
      @PathVariable Long categoryId
  ) {
    categoryService.delete(categoryId);
    return ResponseEntity
        .noContent()
        .build();
  }

  @DeleteMapping("/categories")
  public ResponseEntity<Void> deleteAllById(
      @RequestParam(value = "categoryId") List<Long> categoryIds
  ) {
    categoryService.deleteAllByCategoryId(categoryIds);
    return ResponseEntity
        .noContent()
        .build();
  }

  @GetMapping("/categories")
  public PageResponse<CategoryDTO> getCategoryByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") final Long memberId,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    return categoryService.getCategoriesByMember(cursorId, pageSize, memberId);
  }

  @GetMapping("/categories/cnt")
  public ResponseEntity<Integer> getCategoryCntByMember(
      @RequestParam(value = "memberId") Long memberId
  ) {
    Integer response = categoryService.getCategoryCntByMember(memberId);
    return ResponseEntity.ok(response);
  }
}
