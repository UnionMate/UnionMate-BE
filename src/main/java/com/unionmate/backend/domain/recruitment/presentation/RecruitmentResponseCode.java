package com.unionmate.backend.domain.recruitment.presentation;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.global.response.ResponseCodeInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitmentResponseCode implements ResponseCodeInterface {
	CREATE_RECRUITMENT(200, HttpStatus.OK, "지원서 양식 생성에 성공했습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
