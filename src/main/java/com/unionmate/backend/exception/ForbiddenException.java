package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {

  public ForbiddenException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.FORBIDDEN);
  }
}
