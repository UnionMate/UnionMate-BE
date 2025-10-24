package com.unionmate.backend.global.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentMemberId {

  /**
   * 사용자 ID가 필수인지 여부
   *
   * @return true이면 토큰이 없거나 유효하지 않을 때 예외 발생, false이면 null 반환
   */
  boolean required() default true;
}
