package org.pickly.service.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

}
