package com.unionmate.backend.domain.recruitment.presentation;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.global.response.ResponseCodeInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitmentResponseCode implements ResponseCodeInterface {
	CREATE_RECRUITMENT(200, HttpStatus.OK, "지원서 양식 생성에 성공했습니다."),
	UPDATE_RECRUITMENT(200, HttpStatus.OK, "지원서 양식 수정에 성공했습니다."),
	GET_RECRUITMENTS(200, HttpStatus.OK, "지원서 양식 목록 조회에 성공했습니다."),
	GET_RECRUITMENT(200, HttpStatus.OK, "지원서 양식 조회에 성공했습니다."),
	DELETE_RECRUITMENT(200, HttpStatus.OK, "지원서 삭제에 성공했습니다."),
	RECRUITMENT_TOGGLE_ACTIVATION(200, HttpStatus.OK, "학생회 모집 게시 상태 변경에 성공했습니다."),
	SEND_RESULT_MAIL(204, HttpStatus.NO_CONTENT, "결과 메일 전송에 성공했습니다."),
	GET_RECRUITMENT_RESULT(200, HttpStatus.OK, "모집 결과 조회에 성공했습니다.");;

	private final int code;
	private final HttpStatus status;
	private final String message;
}
