package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class InvalidRecruitmentStatusException extends ApplicationException {
	public InvalidRecruitmentStatusException() {
		super(ErrorCode.RECRUITMENT_STATUS_INVALID, HttpStatus.BAD_REQUEST);
	}
}
