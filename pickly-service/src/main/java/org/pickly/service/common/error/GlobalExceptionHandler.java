package org.pickly.service.common.error;

import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"org.pickly.common", "org.pickly.service"})
public class GlobalExceptionHandler {

  private static final String EXCEPTION_LOG_TEMPLATE = "code = {}, message = {}";

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {

    String code = e.getCode();
    String message = e.getMessage();
    HttpStatus status = e.getHttpStatus();

    log.error(EXCEPTION_LOG_TEMPLATE, code, message);
    final ErrorResponse response = ErrorResponse.of(e);

    return ResponseEntity.status(status).body(response);
  }

}
