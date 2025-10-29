package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ApplicationEvaluationForbiddenException extends ApplicationException {
	public ApplicationEvaluationForbiddenException() {
		super(ErrorCode.APPLICATION_EVALUATION_FORBIDDEN, HttpStatus.FORBIDDEN);
	}
}
