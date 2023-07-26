package org.pickly.service.common.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.error.exception.BusinessException;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private String message;
  private String code;

  private ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static ErrorResponse of(BusinessException e) {
    return new ErrorResponse(e.getCode(), e.getMessage());
  }

}