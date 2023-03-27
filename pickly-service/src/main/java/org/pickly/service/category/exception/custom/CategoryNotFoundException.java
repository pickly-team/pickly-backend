package org.pickly.service.category.exception.custom;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(Long categoryId) {
    super("요청한 카테고리가 존재하지 않습니다. memberId : " + categoryId);
  }
}

