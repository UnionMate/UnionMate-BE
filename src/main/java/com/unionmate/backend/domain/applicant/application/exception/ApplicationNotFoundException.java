package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ApplicationNotFoundException extends ApplicationException {
	public ApplicationNotFoundException() {
		super(ErrorCode.APPLICATION_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
