package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {

  public NotFoundException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.NOT_FOUND);
  }
}
