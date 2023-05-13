package org.pickly.service.category.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.dto.service.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.repository.interfaces.CategoryQueryRepository;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.exception.custom.MemberNotFoundException;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryQueryRepository categoryQueryRepository;

  private static final int LAST_ITEM = 1;

  @Transactional
  public Category create(CategoryRequestDTO dto) {
    Member member = memberRepository.findById(dto.memberId())
        .orElseThrow(() -> new MemberNotFoundException(dto.memberId()));

    return categoryRepository.save(new Category(member, false, dto.name(), ""));
  }

  @Transactional
  public Category update(Long categoryId, CategoryUpdateRequestDTO dto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow();

    return category.update(dto.isAutoDeleteMode(), dto.name(), dto.emoji());
  }

  @Transactional
  public void delete(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow();
    category.delete();
  }

  @Transactional
  public void deleteAllByCategoryId(List<Long> ids) {
    List<Category> categories = categoryRepository.findAllByCategoryId(ids);
    categories.stream().forEach(category -> category.delete());
  }



  @Override
  public Integer getCategoryCntByMember(Long memberId) {
    Integer cnt = categoryRepository.getCategoryCntByMemberId(memberId);
    return cnt == null ? 0 : cnt;
  }

  @Override
  public PageResponse<CategoryDTO> getCategoriesByMember(
      final Long cursorId,
      final Integer pageSize, 
      final Long memberId) {
    PageRequest pageRequest = makePageRequest(cursorId, pageSize);
    List<Category> categories = categoryQueryRepository.findAllByMemberId(pageRequest, memberId);
    return makeResponse(pageRequest.getPageSize(), categories);
  }

  private <T> List<T> mapToDtoList(final List<Category> categories,
      final Function<Category, T> mapper) {
    return categories.stream().map(mapper).toList();
  }

  private PageResponse<CategoryDTO> makeResponse(final int pageSize, List<Category> categories) {
    int contentSize = categories.size();
    categories = removeElement(categories, contentSize, pageSize);
    List<CategoryDTO> contents = mapToDtoList(categories, CategoryDTO::from);
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private List<Category> removeElement(final List<Category> categoryList,
      final int size,
      final int pageSize) {
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
