package com.unionmate.backend.domain.auth.presentation;

import com.unionmate.backend.global.response.ResponseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthResponseCode implements ResponseCodeInterface {

  MANAGER_REGISTER_SUCCESS(201, HttpStatus.CREATED, "회원가입에 성공했습니다"),
  MANAGER_LOGIN_SUCCESS(200, HttpStatus.OK, "로그인에 성공했습니다"),
  REISSUE_SUCCESS(200, HttpStatus.OK, "토큰 reissue에 성공했습니다"),
  ;

  private final int code;
  private final HttpStatus status;
  private final String message;
}
