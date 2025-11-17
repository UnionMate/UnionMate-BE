package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmailAuthorizeFailException extends ApplicationException {

  public EmailAuthorizeFailException() {
    super(ErrorCode.EMAIL_AUTHORIZE_FAIL, HttpStatus.UNAUTHORIZED);
  }
}
