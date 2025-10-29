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
	PLURAL_SELECT_INVALID("중복 선택이 불가능한 항목입니다.", 2107),
	RECRUITMENT_CLOSED("현재 지원 기간이 아닙니다.", 2108),
	ITEM_ANSWER_DUPLICATE("동일한 질문에 중복된 답변을 하였습니다.", 2109),
	APPLICATION_NOT_FOUND("해당 지원서를 찾을 수 없습니다.", 2110),
	APPLICATION_UPDATE_INVALID("해당 지원서를 수정할 수 있는 기간이 아닙니다.", 2111),

	// Comment 관련
	COMMENT_NOT_FOUND("해당 코멘트를 찾을 수 없습니다.", 2130),
	COMMENT_APPLICATION_MISMATCH("코멘트와 지원서가 일치하지 않습니다.", 2131),
	COMMENT_FORBIDDEN("코멘트에 접근 권한이 없습니다.", 2132);

	private final String message;
	private final Integer code;
}
