package com.unionmate.exception;

import org.springframework.http.HttpStatus;

public class RequestTimeoutException extends ApplicationException {

  public RequestTimeoutException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.REQUEST_TIMEOUT);
  }
}
