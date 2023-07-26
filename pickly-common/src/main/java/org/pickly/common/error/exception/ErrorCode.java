package org.pickly.common.error.exception;


import org.springframework.http.HttpStatus;

public interface ErrorCode {

  HttpStatus getStatus();
  String getCode();
  String getMessage();

}