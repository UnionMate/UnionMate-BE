package com.unionmate.backend.exception.common;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends ApplicationException {

  public InvalidJwtException() {
    super(CommonErrorInfo.INVALID_JWT, HttpStatus.UNAUTHORIZED);
  }
}
