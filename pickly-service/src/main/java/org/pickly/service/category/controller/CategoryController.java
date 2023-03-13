package org.pickly.service.category.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.dto.controller.CategoryMapper;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryResponseDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
        @RequestBody @Valid final CategoryRequestDTO dto) {
        Category category = categoryService.create(dto);

        return ResponseEntity
            .ok(CategoryMapper.toResponseDTO(category));
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> update(
        @PathVariable @Positive Long categoryId,
        @RequestBody @Valid final CategoryUpdateRequestDTO dto
    ) {
        Category category = categoryService.update(categoryId, dto);

        return ResponseEntity
            .ok(CategoryMapper.toResponseDTO(category));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long categoryId
    ) {
        categoryService.delete(categoryId);
        return ResponseEntity
            .noContent()
            .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllById(
        @RequestParam(value = "categoryId") List<Long> categoryIds
    ) {
        categoryService.deleteAllByCategoryId(categoryIds);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategoryByMember(
        @RequestParam(value = "memberId") Long memberId
    ) {
        List<CategoryResponseDTO> response = categoryService.getCategoryByMember(memberId)
            .stream()
            .map(category -> CategoryMapper.toResponseDTO(category))
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cnt")
    public ResponseEntity<Integer> getCategoryCntByMember(
        @RequestParam(value = "memberId") Long memberId
    ) {
        Integer response = categoryService.getCategoryCntByMember(memberId);
        return ResponseEntity.ok(response);
    }
}
