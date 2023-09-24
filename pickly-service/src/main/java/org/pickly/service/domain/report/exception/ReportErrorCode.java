package org.pickly.service.domain.report.exception;

import lombok.Getter;
import org.pickly.service.common.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum ReportErrorCode implements ErrorCode {

  REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 신고 정보입니다."),
  ALREADY_REPORT(HttpStatus.CONFLICT, "R002", "이미 신고했습니다."),
  CAN_NOT_REPORT_SELF(HttpStatus.BAD_REQUEST, "R003", "자기 자신을 신고할 수 없습니다."),
  ;

  private final HttpStatus status;
  private final String code;
  private final String message;

  ReportErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
