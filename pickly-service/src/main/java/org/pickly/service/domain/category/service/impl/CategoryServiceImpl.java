package org.pickly.service.domain.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.category.common.CategoryMapper;
import org.pickly.service.domain.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.controller.request.CategoryRequestDTO;
import org.pickly.service.domain.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.exception.CategoryException;
import org.pickly.service.domain.category.repository.interfaces.CategoryJdbcRepository;
import org.pickly.service.domain.category.repository.interfaces.CategoryRepository;
import org.pickly.service.domain.category.service.dto.CategoryDTO;
import org.pickly.service.domain.category.service.interfaces.CategoryService;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.exception.MemberException;
import org.pickly.service.domain.member.repository.interfaces.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryJdbcRepository categoryJdbcRepository;
  private final CategoryMapper categoryMapper;
  private final ApplicationEventPublisher eventPublisher;

  private static final int MAX_CATEGORY_CNT = 20;

  @Transactional
  public void create(Long memberId, List<CategoryRequestDTO> requests) {
    List<Category> categories = new ArrayList<>();
    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberException.MemberNotFoundException::new);
    int orderNum = getNewOrderNum(memberId, requests.size());
    for (CategoryRequestDTO dto : requests) {
      categories.add(
          new Category(member, dto.name(), dto.emoji(), orderNum)
      );
      orderNum++;
    }
    categoryJdbcRepository.createCategories(categories);
  }

  private int getNewOrderNum(Long memberId, int requestSize) {
    List<Category> savedCategories = categoryRepository.findAllCategoryByMemberId(memberId);
    int savedSize = savedCategories.size();
    if (savedSize == MAX_CATEGORY_CNT || savedSize + requestSize > MAX_CATEGORY_CNT) {
      throw new CategoryException.ExceedMaxCategorySizeException();
    }
    return savedCategories.get(savedSize - 1).getOrderNum() + 1;
  }

  @Override
  public CategoryDTO findById(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(CategoryException.CategoryNotFoundException::new);
    return categoryMapper.toDto(category);
  }

  @Transactional
  public Category update(Long categoryId, CategoryUpdateRequestDTO dto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow();

    return category.update(dto.name(), dto.emoji());
  }

  @Transactional
  public void updateOrderNum(List<CategoryOrderNumUpdateReq> requests) {

    if (!checkOrderNumUnique(requests)) {
      throw new CategoryException.CategoryOrderNumDuplicateException();
    }

    try {
      categoryJdbcRepository.updateCategoryOrderNums(requests);
    } catch (DataIntegrityViolationException e) {
      throw new CategoryException.CategoryOrderNumDuplicateException();
    }

  }

  private boolean checkOrderNumUnique(List<CategoryOrderNumUpdateReq> requests) {
    Set<Integer> orderNums = new HashSet<>();
    Set<Long> categoryIds = new HashSet<>();
    for (CategoryOrderNumUpdateReq req : requests) {
      orderNums.add(req.getOrderNum());
      categoryIds.add(req.getCategoryId());
    }
    return orderNums.size() == requests.size() && categoryIds.size() == requests.size();
  }

  @Transactional
  public void delete(Long categoryId) {
    Category category = categoryRepository.findById(categoryId).orElseThrow();
    category.delete();
    eventPublisher.publishEvent(category.getId());
  }

  @Transactional
  public void deleteAllByCategoryId(List<Long> ids) {
    List<Category> categories = categoryRepository.findAllByCategoryId(ids);
    categories.forEach(BaseEntity::delete);
  }

  @Override
  public Integer getCategoryCntByMember(Long memberId) {
    Integer cnt = categoryRepository.getCategoryCntByMemberId(memberId);
    return cnt == null ? 0 : cnt;
  }

  @Override
  public List<CategoryDTO> getCategoriesByMember(Long memberId) {
    List<Category> categories = categoryRepository.findAllCategoryByMemberId(memberId);
    return categories.stream().map(CategoryDTO::from).toList();
  }

}
