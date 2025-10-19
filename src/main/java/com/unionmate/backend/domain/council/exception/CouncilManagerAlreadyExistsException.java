package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CouncilManagerAlreadyExistsException extends ApplicationException {
	public CouncilManagerAlreadyExistsException() {
		super(ErrorCode.COUNCIL_MANAGER_ALREADY_EXISTS, HttpStatus.CONFLICT);
	}
}