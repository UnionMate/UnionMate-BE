package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class NotCouncilViceException extends ApplicationException {
	public NotCouncilViceException() {
		super(ErrorCode.NOT_COUNCIL_VICE, HttpStatus.FORBIDDEN);
	}
}