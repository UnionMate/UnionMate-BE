package com.unionmate.backend.domain.recruitment.application.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	RECRUITMENT_FORM_NOT_FOUND("해당 지원서 양식을 찾을 수 없습니다.", 2100),
	ITEM_TYPE_NOT_EXIST("해당 종류의 항목은 존재하지 않습니다.", 2101),
	NOT_RECRUITMENT_COUNCIL_MEMBER("해당 지원서 양식과 관련된 학생회 멤버가 아닙니다.", 2102),
	ACTIVE_RECRUITMENT_CANNOT_CHANGE("현재 활성화된 양식은 삭제 또는 수정할 수 없습니다.", 2103),
	RECRUITMENT_HAS_APPLICATION_CANNOT_CHANGE("지원자가 존재하는 지원서의 양식을 삭제 또는 수정할 수 없습니다.", 2104);

	private final String message;
	private final Integer code;
}
