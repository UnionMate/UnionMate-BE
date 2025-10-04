package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

  public BadRequestException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.BAD_REQUEST);
  }
}
