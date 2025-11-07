package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentActivationInvalidPeriodException extends ApplicationException {
	public RecruitmentActivationInvalidPeriodException() {
		super(ErrorCode.RECRUITMENT_ACTIVATION_INVALID_PERIOD, HttpStatus.BAD_REQUEST);
	}
}
