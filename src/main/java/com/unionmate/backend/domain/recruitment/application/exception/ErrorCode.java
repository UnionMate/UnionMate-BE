package com.unionmate.backend.domain.recruitment.application.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	RECRUITMENT_FORM_NOT_FOUND("해당 지원서 양식을 찾을 수 없습니다.", 2100);

	private final String message;
	private final Integer code;
}
