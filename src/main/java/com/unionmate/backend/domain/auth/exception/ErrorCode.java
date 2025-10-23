package com.unionmate.backend.domain.auth.exception;

import com.unionmate.backend.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
  EXIST_EMAIL("이미 존재하는 이메일입니다", 3000),
  ;

  private final String message;
  private final Integer code;
}
