package org.pickly.service.domain.category.repository.interfaces;

import org.pickly.service.domain.category.dto.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.entity.Category;

import java.util.List;

public interface CategoryJdbcRepository {

  void createCategories(List<Category> categories);

  void updateCategoryOrderNums(List<CategoryOrderNumUpdateReq> reqs);

}
