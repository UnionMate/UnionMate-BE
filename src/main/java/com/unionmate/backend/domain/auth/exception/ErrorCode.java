package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
  EXIST_EMAIL("이미 존재하는 이메일입니다", 3000),
  PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다", 3001),
  ;

  private final String message;
  private final Integer code;
}
