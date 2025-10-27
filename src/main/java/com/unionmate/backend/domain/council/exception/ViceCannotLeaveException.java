package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ViceCannotLeaveException extends ApplicationException {
	public ViceCannotLeaveException() {
		super(ErrorCode.VICE_CANNOT_LEAVE, HttpStatus.FORBIDDEN);
	}
}
