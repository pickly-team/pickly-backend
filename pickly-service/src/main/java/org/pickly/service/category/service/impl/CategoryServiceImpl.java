package org.pickly.service.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.category.dto.controller.CategoryCreateReq;
import org.pickly.service.category.dto.controller.CategoryUpdateReq;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final MemberService memberService;

  @Transactional
  public void create(Long memberId, CategoryCreateReq request) {
    Category category = new Category(memberService.findById(memberId), request.getCategoryName());
    categoryRepository.save(category);
  }
}
