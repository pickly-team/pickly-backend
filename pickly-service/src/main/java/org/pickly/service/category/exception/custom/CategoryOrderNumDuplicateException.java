package org.pickly.service.category.exception.custom;

public class CategoryOrderNumDuplicateException extends RuntimeException {
  public CategoryOrderNumDuplicateException() {
    super("카테고리 순서 입력값이 잘못되었습니다.");
  }
}
