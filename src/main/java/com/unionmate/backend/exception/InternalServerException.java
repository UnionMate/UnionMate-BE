package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ApplicationException {

  public InternalServerException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
