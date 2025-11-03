package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentDeactivationNotAllowedException extends ApplicationException {
	public RecruitmentDeactivationNotAllowedException() {
		super(ErrorCode.RECRUITMENT_DEACTIVATION_NOT_ALLOWED, HttpStatus.BAD_REQUEST);
	}
}
