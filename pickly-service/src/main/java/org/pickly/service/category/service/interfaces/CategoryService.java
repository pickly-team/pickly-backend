package org.pickly.service.category.service.interfaces;

import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.entity.Category;

public interface CategoryService {
  Category create(CategoryRequestDTO request);

  Category update(Long categoryId, CategoryUpdateRequestDTO dto);
}
