package org.pickly.service.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.category.dto.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.dto.service.CategoryDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.repository.interfaces.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.pickly.service.domain.category.exception.CategoryException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryReadService {

  private final CategoryRepository categoryRepository;

  private static final int MAX_CATEGORY_CNT = 20;

  public int getNewOrderNum(Long memberId, int requestSize) {
    List<Category> savedCategories = categoryRepository.findAllCategoryByMemberId(memberId);
    int savedSize = savedCategories.size();
    if (savedSize == MAX_CATEGORY_CNT || savedSize + requestSize > MAX_CATEGORY_CNT) {
      throw new ExceedMaxCategorySizeException();
    }
    if (savedSize == 0) {
      return 1;
    }
    return savedCategories.get(savedSize - 1).getOrderNum() + 1;
  }

  public Category findById(Long categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(CategoryNotFoundException::new);
  }

  public void checkOrderNumUnique(List<CategoryOrderNumUpdateReq> requests) {
    if (!isOrderNumUnique(requests)) {
      throw new CategoryOrderNumDuplicateException();
    }
  }

  private boolean isOrderNumUnique(List<CategoryOrderNumUpdateReq> requests) {
    Set<Integer> orderNums = new HashSet<>();
    Set<Long> categoryIds = new HashSet<>();
    for (CategoryOrderNumUpdateReq req : requests) {
      orderNums.add(req.getOrderNum());
      categoryIds.add(req.getCategoryId());
    }
    return orderNums.size() == requests.size() && categoryIds.size() == requests.size();
  }

  public List<Category> findByIds(List<Long> ids) {
    return categoryRepository.findAllByCategoryId(ids);
  }

  public Integer getCategoryCntByMember(Long memberId) {
    Integer cnt = categoryRepository.getCategoryCntByMemberId(memberId);
    return cnt == null ? 0 : cnt;
  }

  public List<CategoryDTO> getCategoriesByMember(Long memberId) {
    List<Category> categories = categoryRepository.findAllCategoryByMemberId(memberId);
    return categories.stream().map(CategoryDTO::from).toList();
  }

  public void existsById(Long categoryId) {
    if (!categoryRepository.existsByIdAndDeletedAtIsNull(categoryId)) {
      throw new CategoryNotFoundException();
    }
  }

}
