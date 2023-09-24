package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.controller.request.CategoryRequestDTO;
import org.pickly.service.domain.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.service.CategoryReadService;
import org.pickly.service.domain.category.service.CategoryWriteService;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

  private final CategoryWriteService categoryWriteService;
  private final CategoryReadService categoryReadService;
  private final MemberService memberService;

  public void create(Long memberId, List<CategoryRequestDTO> requests) {
    var member = memberService.findById(memberId);
    int nextOrderNum = categoryReadService.getNewOrderNum(memberId, requests.size());

    categoryWriteService.create(member, requests, nextOrderNum);
  }

  public Category update(Long categoryId, CategoryUpdateRequestDTO request) {
    var category = categoryReadService.findById(categoryId);
    return categoryWriteService.update(category, request);
  }

  public void updateOrderNum(List<CategoryOrderNumUpdateReq> requests) {
    categoryReadService.checkOrderNumUnique(requests);
    categoryWriteService.updateOrderNum(requests);
  }

  public void delete(Long categoryId) {
    var category = categoryReadService.findById(categoryId);
    categoryWriteService.delete(category);
  }

  public void delete(List<Long> categoryIds) {
    List<Category> categories = categoryReadService.findByIds(categoryIds);
    categoryWriteService.delete(categories);
  }

}
