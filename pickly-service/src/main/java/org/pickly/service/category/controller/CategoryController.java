package org.pickly.service.category.controller;

import lombok.RequiredArgsConstructor;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

  private final CategoryService categoryService;

}