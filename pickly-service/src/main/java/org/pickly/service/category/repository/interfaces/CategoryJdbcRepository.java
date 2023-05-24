package org.pickly.service.category.repository.interfaces;

import org.pickly.service.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.category.entity.Category;

import java.util.List;

public interface CategoryJdbcRepository {

  void createCategories(List<Category> categories);

  void updateCategoryOrderNums(List<CategoryOrderNumUpdateReq> reqs);

}
