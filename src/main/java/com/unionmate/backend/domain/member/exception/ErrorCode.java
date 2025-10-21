package com.unionmate.backend.domain.member.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다", 2100),
	SCHOOL_NOT_FOUND("해당 학교를 찾을 수 없습니다", 2101),
	INVALID_EMAIL_FORMAT("올바르지 않은 이메일 형식입니다", 2102);

	private final String message;
	private final Integer code;
}
