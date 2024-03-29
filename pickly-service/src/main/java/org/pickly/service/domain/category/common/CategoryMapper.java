package org.pickly.service.domain.category.common;

import org.pickly.service.domain.category.dto.controller.response.CategoryRes;
import org.pickly.service.domain.category.dto.controller.response.CategoryResponseDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.dto.service.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public CategoryResponseDTO toResponseDTO(final Category category) {
    return new CategoryResponseDTO(
        category.getId(),
        category.getName(),
        category.getEmoji()
    );
  }

  public CategoryRes toResponse(final Category category) {
    return CategoryRes.builder()
        .categoryId(category.getId())
        .name(category.getName())
        .emoji(category.getEmoji())
        .orderNum(category.getOrderNum())
        .build();
  }

  public CategoryRes toResponse(final CategoryDTO dto) {
    return CategoryRes.builder()
        .categoryId(dto.getCategoryId())
        .name(dto.getName())
        .emoji(dto.getEmoji())
        .orderNum(dto.getOrderNum())
        .build();
  }

}
