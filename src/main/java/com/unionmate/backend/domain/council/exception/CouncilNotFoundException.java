package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CouncilNotFoundException extends ApplicationException {
	public CouncilNotFoundException() {
		super(ErrorCode.COUNCIL_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}