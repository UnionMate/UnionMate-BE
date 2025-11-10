package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class TokenIssuanceException extends ApplicationException {

  public TokenIssuanceException() {
    super(ErrorCode.TOKEN_ISSUANCE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
