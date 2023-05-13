package org.pickly.service.category.service.interfaces;

import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.dto.service.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.page.PageResponse;

import java.util.List;

public interface CategoryService {

    void create(Long memberId, List<CategoryRequestDTO> requests);

    Category update(Long categoryId, CategoryUpdateRequestDTO dto);

    void delete(Long categoryId);

    void deleteAllByCategoryId(List<Long> ids);

    Integer getCategoryCntByMember(Long memberId);

    PageResponse<CategoryDTO> getCategoriesByMember(Long cursorId, Integer pageSize, Long memberId);
}
