package com.unionmate.backend.global.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true) // 해당 어노테이션을 통해 @CurrentMemberId가 적용되면 스웨거에 memberId 입력칸이 보이지 않음. 어차피 서버 자동 적용.
public @interface CurrentMemberId {

	/**
	 * 사용자 ID가 필수인지 여부
	 *
	 * @return true이면 토큰이 없거나 유효하지 않을 때 예외 발생, false이면 null 반환
	 */
	boolean required() default true;
}
