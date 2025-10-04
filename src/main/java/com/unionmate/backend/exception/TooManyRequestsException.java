package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends ApplicationException {

  public TooManyRequestsException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.TOO_MANY_REQUESTS);
  }
}
