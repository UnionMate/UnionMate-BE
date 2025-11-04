package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentActivationExpiredException extends ApplicationException {
	public RecruitmentActivationExpiredException() {
		super(ErrorCode.RECRUITMENT_ACTIVATION_EXPIRED, HttpStatus.BAD_REQUEST);
	}
}
