package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmailDuplicateException extends ApplicationException {

  public EmailDuplicateException() {
    super(ErrorCode.EXIST_EMAIL, HttpStatus.CONFLICT);
  }
}
