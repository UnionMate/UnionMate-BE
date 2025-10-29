package com.unionmate.backend.domain.recruitment.application.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	RECRUITMENT_FORM_NOT_FOUND("해당 지원서 양식을 찾을 수 없습니다.", 2100),
	ITEM_TYPE_NOT_EXIST("해당 종류의 항목은 존재하지 않습니다.", 2101),
	NOT_RECRUITMENT_COUNCIL_MEMBER("해당 지원서 양식과 관련된 학생회 멤버가 아닙니다.", 2102);

	private final String message;
	private final Integer code;
}
