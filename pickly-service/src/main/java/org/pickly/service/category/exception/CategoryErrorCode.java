package org.pickly.service.category.exception;

import lombok.Getter;
import org.pickly.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum CategoryErrorCode implements ErrorCode {

  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 카테고리 정보입니다."),
  CATEGORY_ORDER_NUM_DUPLICATE(HttpStatus.BAD_REQUEST, "C002", "카테고리 순서 입력값이 잘못되었습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  CategoryErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
