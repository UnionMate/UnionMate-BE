package com.unionmate.backend.domain.council.presentation;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.global.response.ResponseCodeInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouncilResponseCode implements ResponseCodeInterface {
	CREATE_COUNCIL(201, HttpStatus.CREATED, "학생회 생성에 성공했습니다."),
	COUNCIL_MANAGER_SIGNUP(200, HttpStatus.OK, "학생회 관리자 등록에 성공했습니다."),
	DELEGATE_VICE(200, HttpStatus.OK, "회장 권한 위임에 성공했습니다."),
	GET_ALL_COUNCIL_MEMBERS(200, HttpStatus.OK, "학생회 멤버 조회에 성공했습니다."),
	UPDATE_COUNCIL_NAME(200, HttpStatus.OK, "학생회 이름 변경에 성공했습니다."),
	UPDATE_INVITATION_CODE(200, HttpStatus.OK, "초대 코드 변경에 성공했습니다."),
	REMOVE_COUNCIL_MEMBER(200, HttpStatus.OK, "학생회 멤버 탈퇴에 성공했습니다."),
	COUNCIL_DOCUMENT_LIST(200, HttpStatus.OK, "학생회 서류 심사 목록 조회에 성공했습니다."),
	COUNCIL_INTERVIEW_LIST(200, HttpStatus.OK, "학생회 면접 심사 목록 조회에 성공했습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
