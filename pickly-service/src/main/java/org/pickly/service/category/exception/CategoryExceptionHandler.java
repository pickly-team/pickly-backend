package org.pickly.service.category.exception;

import org.pickly.common.error.ErrorResponse;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.category.exception.custom.CategoryOrderNumDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler {

  @ExceptionHandler(CategoryOrderNumDuplicateException.class)
  protected ResponseEntity<ErrorResponse> handleCategoryOrderNumDuplicateException(
      CategoryOrderNumDuplicateException e) {
    final ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
