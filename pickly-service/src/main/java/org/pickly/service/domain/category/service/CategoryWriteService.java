package org.pickly.service.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.category.dto.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.dto.controller.request.CategoryRequestDTO;
import org.pickly.service.domain.category.dto.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.exception.CategoryException;
import org.pickly.service.domain.category.repository.interfaces.CategoryJdbcRepository;
import org.pickly.service.domain.member.entity.Member;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryWriteService {

  private final CategoryJdbcRepository categoryJdbcRepository;

  public void create(Member member, List<CategoryRequestDTO> requests, int nextOrderNum) {
    List<Category> categories = new ArrayList<>();
    for (CategoryRequestDTO dto : requests) {
      categories.add(
          new Category(member, dto.name(), dto.emoji(), nextOrderNum)
      );
      nextOrderNum++;
    }
    categoryJdbcRepository.createCategories(categories);
  }

  public Category update(Category category, CategoryUpdateRequestDTO dto) {
    return category.update(dto.name(), dto.emoji());
  }

  public void updateOrderNum(List<CategoryOrderNumUpdateReq> requests) {
    try {
      categoryJdbcRepository.updateCategoryOrderNums(requests);
    } catch (DataIntegrityViolationException e) {
      throw new CategoryException.CategoryOrderNumDuplicateException();
    }
  }

  public void delete(Category category) {
    category.delete();
  }

  public void delete(List<Category> categories) {
    categories.forEach(BaseEntity::delete);
  }

}
