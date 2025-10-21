package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class TextTooLongException extends ApplicationException {
	public TextTooLongException() {
		super(ErrorCode.TEXT_TOO_LONG, HttpStatus.BAD_REQUEST);
	}
}
