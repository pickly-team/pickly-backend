package org.pickly.service.category.repository.interfaces;

import java.util.List;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.page.PageRequest;

public interface CategoryQueryRepository {

  List<Category> findAllByMemberId(PageRequest pageRequest, Long memberId);

}
