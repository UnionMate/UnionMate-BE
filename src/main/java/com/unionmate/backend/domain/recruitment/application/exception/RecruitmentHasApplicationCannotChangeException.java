package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentHasApplicationCannotChangeException extends ApplicationException {
	public RecruitmentHasApplicationCannotChangeException() {
		super(ErrorCode.RECRUITMENT_HAS_APPLICATION_CANNOT_CHANGE, HttpStatus.FORBIDDEN);
	}
}
