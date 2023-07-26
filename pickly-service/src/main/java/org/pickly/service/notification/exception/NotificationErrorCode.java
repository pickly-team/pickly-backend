package org.pickly.service.notification.exception;

import lombok.Getter;
import org.pickly.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum NotificationErrorCode implements ErrorCode {

  NOTIFICATION_STANDARD_NOT_FOUND(HttpStatus.NOT_FOUND, "NS001", "유저에게 알림 설정 정보가 존재하지 않습니다."),
  NOTIFICATION_STANDARD_ALREADY_EXIST(HttpStatus.CONFLICT, "NS002", "유저에게 알림 설정 정보가 이미 존재합니다."),
  NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "N001", "존재하지 않는 알림 정보 입니다.")
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  NotificationErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
