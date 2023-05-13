package org.pickly.service.category.service.interfaces;

import java.util.List;
import org.pickly.service.category.dto.controller.CategoryRequestDTO;
import org.pickly.service.category.dto.controller.CategoryResponseDTO;
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO;
import org.pickly.service.category.dto.service.CategoryDTO;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;

public interface CategoryService {

    Category create(CategoryRequestDTO request);

    Category update(Long categoryId, CategoryUpdateRequestDTO dto);

    void delete(Long categoryId);

    void deleteAllByCategoryId(List<Long> ids);


    Integer getCategoryCntByMember(Long memberId);

    PageResponse<CategoryDTO> getCategoriesByMember(PageRequest pageRequest, Long memberId);
}
