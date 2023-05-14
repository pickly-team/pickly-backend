package org.pickly.service.category.service.interfaces;

import org.pickly.service.category.dto.controller.CategoryOrderNumUpdateReq;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.dto.service.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.page.PageResponse;

import java.util.List;

public interface CategoryService {

    void create(Long memberId, List<CategoryRequestDTO> requests);

    Category update(Long categoryId, CategoryUpdateRequestDTO dto);

    void updateOrderNum(List<CategoryOrderNumUpdateReq> requests);

    void delete(Long categoryId);

    void deleteAllByCategoryId(List<Long> ids);

    Integer getCategoryCntByMember(Long memberId);

    PageResponse<CategoryDTO> getCategoriesWithPagingByMember(Long cursorId, Integer pageSize, Long memberId);

    List<CategoryDTO> getCategoriesByMember(Long memberId);

}
