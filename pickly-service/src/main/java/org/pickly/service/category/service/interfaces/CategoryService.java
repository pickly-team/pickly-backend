package org.pickly.service.category.service.interfaces;

import org.pickly.service.category.dto.controller.CategoryCreateReq;

public interface CategoryService {

  void create(Long memberId, CategoryCreateReq categoryName);
}
