package com.unionmate.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {

  public ForbiddenException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.FORBIDDEN);
  }
}
