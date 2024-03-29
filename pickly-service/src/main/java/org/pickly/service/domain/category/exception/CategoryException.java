package org.pickly.service.domain.category.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class CategoryException extends BusinessException {

  protected CategoryException(CategoryErrorCode errorCode) {
    super(errorCode);
  }

  public static class CategoryNotFoundException extends CategoryException {
    public CategoryNotFoundException() {
      super(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }
  }

  public static class CategoryOrderNumDuplicateException extends CategoryException {
    public CategoryOrderNumDuplicateException() {
      super(CategoryErrorCode.CATEGORY_ORDER_NUM_DUPLICATE);
    }
  }

  public static class ExceedMaxCategorySizeException extends CategoryException {
    public ExceedMaxCategorySizeException() {
      super(CategoryErrorCode.EXCEED_MAX_CATEGORY_SIZE);
    }
  }

}
