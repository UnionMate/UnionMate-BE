package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class InterviewEvaluationNotFoundException extends ApplicationException {
	public InterviewEvaluationNotFoundException() {
		super(ErrorCode.INTERVIEW_EVALUATION_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
