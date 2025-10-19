package com.unionmate.backend.domain.council.presentation;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.global.response.ResponseCodeInterface;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouncilResponseCode implements ResponseCodeInterface {
	CREATE_COUNCIL(201, HttpStatus.CREATED, "학생회 생성에 성공했습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
