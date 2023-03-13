package org.pickly.service.category.service.interfaces;

import org.pickly.service.category.dto.controller.CategoryCreateReq;
import org.pickly.service.category.dto.controller.CategoryUpdateReq;

public interface CategoryService {

  void create(Long memberId, CategoryCreateReq categoryName);
  void update(Long memberId, Long categoryId, CategoryUpdateReq request);
}
