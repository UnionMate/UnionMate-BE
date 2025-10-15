package com.unionmate.backend.domain.applicant.application.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	ITEM_NOT_FOUND("해당 문항을 찾을 수 없습니다.", 2102),
	ITEM_TYPE_MISMATCH("문항 형식이 일치하지 않습니다.", 2103),
	REQUIRED_ANSWER_MISSING("필수 문항의 답변이 누락되었습니다.", 2104),
	TEXT_TOO_LONG("최대 글자 수를 초과했습니다.", 2105),
	OPTION_INVALID("유효하지 않은 선택지입니다.", 2106),
	RECRUITMENT_CLOSED("현재 지원 기간이 아닙니다.", 2107);

	private final String message;
	private final Integer code;
}
