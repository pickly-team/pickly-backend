package org.pickly.service.domain.category.service.interfaces;

import org.pickly.service.domain.category.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.controller.request.CategoryRequestDTO;
import org.pickly.service.domain.category.controller.request.CategoryUpdateRequestDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.service.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    void create(Long memberId, List<CategoryRequestDTO> requests);

    CategoryDTO findById(Long categoryId);

    Category update(Long categoryId, CategoryUpdateRequestDTO dto);

    void updateOrderNum(List<CategoryOrderNumUpdateReq> requests);

    void delete(Long categoryId);

    void deleteAllByCategoryId(List<Long> ids);

    Integer getCategoryCntByMember(Long memberId);

    List<CategoryDTO> getCategoriesByMember(Long memberId);

}
