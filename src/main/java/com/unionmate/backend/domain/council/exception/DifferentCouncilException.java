package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class DifferentCouncilException extends ApplicationException {
	public DifferentCouncilException() {
		super(ErrorCode.DIFFERENT_COUNCIL, HttpStatus.BAD_REQUEST);
	}
}
