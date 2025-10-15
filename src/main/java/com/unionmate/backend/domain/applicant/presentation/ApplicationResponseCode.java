package com.unionmate.backend.domain.applicant.presentation;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.global.response.ResponseCodeInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationResponseCode implements ResponseCodeInterface {
	SUBMIT_APPLICATION(200, HttpStatus.OK, "지원서가 성공적으로 제출되었습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
