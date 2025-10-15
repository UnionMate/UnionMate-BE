package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentInvalidException extends ApplicationException {
	public RecruitmentInvalidException() {
		super(ErrorCode.RECRUITMENT_CLOSED, HttpStatus.BAD_REQUEST);
	}
}
