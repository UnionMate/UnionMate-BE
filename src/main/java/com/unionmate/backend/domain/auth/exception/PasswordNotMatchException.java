package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ApplicationException {

  public PasswordNotMatchException() {
    super(ErrorCode.PASSWORD_NOT_MATCH, HttpStatus.UNAUTHORIZED);
  }
}
