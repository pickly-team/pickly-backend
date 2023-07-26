package org.pickly.service.block.exception;

import lombok.Getter;
import org.pickly.service.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum BlockErrorCode implements ErrorCode {

  BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "BL001", "존재하지 않는 차단 정보입니다."),
  ALREADY_BLOCK(HttpStatus.CONFLICT, "BL002", "이미 차단했습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  BlockErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
