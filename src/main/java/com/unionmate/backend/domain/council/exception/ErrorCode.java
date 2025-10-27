package com.unionmate.backend.domain.council.exception;

import com.unionmate.backend.exception.ErrorInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInfo {
	COUNCIL_MANAGER_ALREADY_EXISTS("이미 학생회 관리자로 등록된 회원입니다", 3100),
	COUNCIL_NOT_FOUND("해당 초대코드의 학생회가 존재하지 않습니다", 3101),
	COUNCIL_MANAGER_NOT_FOUND("학생회 관리자 정보를 찾을 수 없습니다", 3102),
	NOT_COUNCIL_VICE("회장 권한이 없습니다", 3103),
	DIFFERENT_COUNCIL("같은 학생회에 속한 멤버가 아닙니다", 3104),
	VICE_CANNOT_LEAVE("회장은 탈퇴할 수 없습니다", 3105);

	private final String message;
	private final Integer code;
}