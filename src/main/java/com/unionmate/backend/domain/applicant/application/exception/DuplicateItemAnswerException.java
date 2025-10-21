package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class DuplicateItemAnswerException extends ApplicationException {
	public DuplicateItemAnswerException() {
		super(ErrorCode.ITEM_ANSWER_DUPLICATE, HttpStatus.BAD_REQUEST);
	}
}
