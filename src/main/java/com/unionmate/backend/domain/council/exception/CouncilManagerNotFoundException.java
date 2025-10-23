package com.unionmate.backend.domain.council.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CouncilManagerNotFoundException extends ApplicationException {
	public CouncilManagerNotFoundException() {
		super(ErrorCode.COUNCIL_MANAGER_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
