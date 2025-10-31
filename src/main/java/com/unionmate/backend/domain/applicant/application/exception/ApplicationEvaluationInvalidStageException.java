package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ApplicationEvaluationInvalidStageException extends ApplicationException {
	public ApplicationEvaluationInvalidStageException() {
		super(ErrorCode.APPLICATION_EVALUATION_INVALID_STAGE, HttpStatus.BAD_REQUEST);
	}
}
