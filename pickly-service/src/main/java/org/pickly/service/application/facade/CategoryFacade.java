package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.service.BookmarkWriteService;
import org.pickly.service.domain.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.controller.request.CategoryRequestDTO;
import org.pickly.service.domain.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.service.CategoryReadService;
import org.pickly.service.domain.category.service.CategoryWriteService;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

  private final CategoryWriteService categoryWriteService;
  private final CategoryReadService categoryReadService;
  private final MemberReadService memberReadService;
  private final BookmarkWriteService bookmarkWriteService;

  public void create(Long memberId, List<CategoryRequestDTO> requests) {
    var member = memberReadService.findById(memberId);
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

  // FIXME: category 삭제와 bookmark 삭제가 같은 트랜잭션 안에 있어야 함. 그래야 롤백되니까!
  // FIXME: 근데 여기에 @Transactional 붙이면 더티체킹이 안먹음 ㅠㅠ 아직 원인 파악 몬함
  public void delete(Long categoryId) {
    Category category = categoryReadService.findById(categoryId);
    categoryWriteService.delete(category);
    bookmarkWriteService.deleteByCategory(category);
  }

  public void delete(List<Long> categoryIds) {
    List<Category> categories = categoryReadService.findByIds(categoryIds);
    categoryWriteService.delete(categories);
    bookmarkWriteService.deleteByCategory(categories);
  }

}
