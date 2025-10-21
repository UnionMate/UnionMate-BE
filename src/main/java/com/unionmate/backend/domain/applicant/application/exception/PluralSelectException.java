package com.unionmate.backend.domain.applicant.application.exception;

import com.unionmate.backend.exception.ApplicationException;

import org.springframework.http.HttpStatus;

public class PluralSelectException extends ApplicationException {
	public PluralSelectException() {
		super(ErrorCode.PLURAL_SELECT_INVALID, HttpStatus.BAD_REQUEST);
	}
}
