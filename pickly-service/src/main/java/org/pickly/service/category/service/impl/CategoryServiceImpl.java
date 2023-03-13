package org.pickly.service.category.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.exception.custom.CategoryNotFoundException;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.category.service.interfaces.CategoryService;
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

  @Transactional
  public Category create(CategoryRequestDTO dto) {
    Member member = memberRepository.findById(dto.memberId())
        .orElseThrow(() -> new MemberNotFoundException(dto.memberId()));

    return categoryRepository.save(new Category(member, false, dto.name(), ""));
  }

  @Transactional
  public Category update(Long categoryId, CategoryUpdateRequestDTO dto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(CategoryNotFoundException::new);

    return category.update(dto.isAutoDeleteMode(), dto.name(), dto.emoji());
  }

  @Transactional
  public void delete(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(CategoryNotFoundException::new);
    category.delete();
  }

  @Transactional
  public void deleteAllByCategoryId(List<Long> ids) {
    List<Category> categories = categoryRepository.findAllByCategoryId(ids);
    categories.stream().forEach(category -> category.delete());
  }

  @Override
  public List<Category> getCategoryByMember(Long memberId) {
    return categoryRepository.findAllByMemberId(memberId);
  }

  @Override
  public Integer getCategoryCntByMember(Long memberId) {
    Integer cnt = categoryRepository.getCategoryCntByMemberId(memberId);
    return cnt == null ? 0 : cnt;
  }
}
