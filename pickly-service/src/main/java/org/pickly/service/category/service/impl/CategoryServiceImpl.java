package org.pickly.service.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.category.common.CategoryMapper;
import org.pickly.service.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.category.controller.request.CategoryRequestDTO;
import org.pickly.service.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.exception.CategoryException;
import org.pickly.service.category.repository.interfaces.CategoryJdbcRepository;
import org.pickly.service.category.repository.interfaces.CategoryQueryRepository;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.category.service.dto.CategoryDTO;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.exception.MemberException;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryQueryRepository categoryQueryRepository;
  private final CategoryJdbcRepository categoryJdbcRepository;
  private final CategoryMapper categoryMapper;
  private final ApplicationEventPublisher eventPublisher;
  private static final int LAST_ITEM = 1;

  @Transactional
  public void create(Long memberId, List<CategoryRequestDTO> requests) {
    List<Category> categories = new ArrayList<>();

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberException.MemberNotFoundException::new);

    Category lastCategory = categoryRepository.findLastCategoryByMemberId(memberId);
    int orderNum = (lastCategory == null) ? 0 : lastCategory.getOrderNum() + 1;

    for (CategoryRequestDTO dto : requests) {
      categories.add(
          new Category(member, dto.name(), dto.emoji(), orderNum)
      );
      orderNum++;
    }

    categoryJdbcRepository.createCategories(categories);
  }

  @Override
  public CategoryDTO findById(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow();
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
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow();
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
  public PageResponse<CategoryDTO> getCategoriesWithPagingByMember(
      final Long cursorId,
      final Integer pageSize, 
      final Long memberId
  ) {
    PageRequest pageRequest = makePageRequest(cursorId, pageSize);
    List<Category> categories = categoryQueryRepository.findAllByMemberId(pageRequest, memberId);
    return makeResponse(pageRequest.getPageSize(), categories);
  }

  @Override
  public List<CategoryDTO> getCategoriesByMember(Long memberId) {
    List<Category> categories = categoryRepository.findAllCategoryByMemberId(memberId);
    return categories.stream().map(CategoryDTO::from).toList();
  }

  private <T> List<T> mapToDtoList(
      final List<Category> categories,
      final Function<Category, T> mapper
  ) {
    return categories.stream().map(mapper).toList();
  }

  private PageResponse<CategoryDTO> makeResponse(final int pageSize, List<Category> categories) {
    int contentSize = categories.size();
    categories = removeElement(categories, contentSize, pageSize);
    List<CategoryDTO> contents = mapToDtoList(categories, CategoryDTO::from);
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private List<Category> removeElement(
      final List<Category> categoryList,
      final int size,
      final int pageSize
  ) {
    if (size - LAST_ITEM >= pageSize) {
      List<Category> resultList = new ArrayList<>(categoryList);
      resultList.remove(size - LAST_ITEM);
      return resultList;
    }
    return categoryList;
  }

  private PageRequest makePageRequest(final Long cursorId, final Integer pageSize) {
    return new PageRequest(cursorId, pageSize);
  }
}
