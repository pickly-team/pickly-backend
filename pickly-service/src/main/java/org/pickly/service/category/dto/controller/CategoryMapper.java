package org.pickly.service.category.dto.controller;

import org.pickly.service.category.entity.Category;

public class CategoryMapper {

  public static CategoryResponseDTO toResponseDTO(final Category category) {
    return new CategoryResponseDTO(
        category.getId(),
        category.getName(),
        category.getEmoji()
    );
  }

}
