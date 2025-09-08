package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApplicationException {

  public UnAuthorizedException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.UNAUTHORIZED);
  }
}
