package org.pickly.common.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final String code;
  private final HttpStatus httpStatus;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.httpStatus = errorCode.getStatus();
    this.code = errorCode.getCode();
  }

}