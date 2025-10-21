package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RequiredAnswerMissingException extends ApplicationException {
	public RequiredAnswerMissingException() {
		super(ErrorCode.REQUIRED_ANSWER_MISSING, HttpStatus.BAD_REQUEST);
	}
}
