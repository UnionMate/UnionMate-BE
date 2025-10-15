package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RequiredItemNotAnsweredException extends ApplicationException {
	public RequiredItemNotAnsweredException() {
		super(ErrorCode.REQUIRED_ANSWER_MISSING, HttpStatus.BAD_REQUEST);
	}
}
