package com.unionmate.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApplicationException{

  public ConflictException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.CONFLICT);
  }
}
