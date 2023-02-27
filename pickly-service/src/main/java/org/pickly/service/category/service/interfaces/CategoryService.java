package org.pickly.service.category.service.interfaces;

import java.util.List;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.entity.Category;

public interface CategoryService {
  Category create(CategoryRequestDTO request);

  Category update(Long categoryId, CategoryUpdateRequestDTO dto);

  void delete(Long categoryId);

  void deleteAllByCategoryId(List<Long> ids);
}
