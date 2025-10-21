package com.unionmate.backend.domain.council.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	COUNCIL_MANAGER_ALREADY_EXISTS("이미 학생회 관리자로 등록된 회원입니다", 3100),
	COUNCIL_NOT_FOUND("해당 초대코드의 학생회가 존재하지 않습니다", 3101);

	private final String message;
	private final Integer code;
}