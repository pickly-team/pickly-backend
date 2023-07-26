package org.pickly.service.common.error;

import lombok.Getter;
import org.pickly.service.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode {

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "서버 내부 에러가 발생했습니다."),
  INVALID_VALUE_ERROR(HttpStatus.BAD_REQUEST, "E002", "요청이 유효하지 않습니다."),
  INVALID_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, "E003", "인증 정보가 유효하지 않습니다.")
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  CommonErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
