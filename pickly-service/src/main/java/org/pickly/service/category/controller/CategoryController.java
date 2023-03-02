package org.pickly.service.category.controller;

import lombok.RequiredArgsConstructor;
import version.VersionResource;
import org.pickly.service.category.service.interfaces.CategoryService;
import org.pickly.service.common.version.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/test")
  @VersionResource(from = ApiVersion.V1)
  public String test() {
    return "test";
  }

}
