package org.pickly.service.friend.exception;

import lombok.Getter;
import org.pickly.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum FriendErrorCode implements ErrorCode {

  FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "존재하지 않는 친구 정보입니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  FriendErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
