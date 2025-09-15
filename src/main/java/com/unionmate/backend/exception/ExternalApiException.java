package com.unionmate.backend.exception;

import org.springframework.http.HttpStatus;

public class ExternalApiException extends ApplicationException {

  public ExternalApiException(ErrorInfo errorInfo) {
    super(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
