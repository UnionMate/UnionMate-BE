package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class RecruitmentNotFoundException extends ApplicationException {
	public RecruitmentNotFoundException() {
		super(ErrorCode.RECRUITMENT_FORM_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
