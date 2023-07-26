package org.pickly.service.member.exception;

import lombok.Getter;
import org.pickly.service.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode {

  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 유저 정보입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  MemberErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
