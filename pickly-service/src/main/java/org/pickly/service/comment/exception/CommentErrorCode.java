package org.pickly.service.comment.exception;

import lombok.Getter;
import org.pickly.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentErrorCode implements ErrorCode {

  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CO001", "존재하지 않는 코멘트 정보입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  CommentErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
