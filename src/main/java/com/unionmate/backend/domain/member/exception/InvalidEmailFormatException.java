package com.unionmate.backend.domain.member.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class InvalidEmailFormatException extends ApplicationException {
	public InvalidEmailFormatException() {
		super(ErrorCode.INVALID_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
	}
}