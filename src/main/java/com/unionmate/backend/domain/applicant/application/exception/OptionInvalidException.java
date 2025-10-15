package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class OptionInvalidException extends ApplicationException {
	public OptionInvalidException() {
		super(ErrorCode.OPTION_NOT_INVALID, HttpStatus.BAD_REQUEST);
	}
}
