package org.pickly.service.bookmark.exception;

import lombok.Getter;
import org.pickly.service.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum BookmarkErrorCode implements ErrorCode {

  BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "존재하지 않는 북마크 정보입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  BookmarkErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
