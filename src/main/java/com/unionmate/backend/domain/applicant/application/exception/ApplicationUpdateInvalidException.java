package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ApplicationUpdateInvalidException extends ApplicationException {
	public ApplicationUpdateInvalidException() {
		super(ErrorCode.APPLICATION_UPDATE_INVALID, HttpStatus.BAD_REQUEST);
	}
}
