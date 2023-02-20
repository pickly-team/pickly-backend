package org.pickly.service.category.repository.interfaces;

import org.pickly.service.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Long save(Category category);

}
