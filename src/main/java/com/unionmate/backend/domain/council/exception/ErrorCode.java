package com.unionmate.backend.domain.council.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	COUNCIL_MANAGER_ALREADY_EXISTS("이미 학생회 관리자로 등록된 회원입니다", 3100);

	private final String message;
	private final Integer code;
}