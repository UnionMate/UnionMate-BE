package com.unionmate.backend.exception.common;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExpireJwtException extends ApplicationException {

  public ExpireJwtException() {
    super(CommonErrorInfo.EXPIRED_JWT, HttpStatus.UNAUTHORIZED);
  }
}
