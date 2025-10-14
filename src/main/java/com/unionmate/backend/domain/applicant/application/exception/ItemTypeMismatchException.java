package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ItemTypeMismatchException extends ApplicationException {
	public ItemTypeMismatchException() {
		super(ErrorCode.ITEM_TYPE_MISMATCH, HttpStatus.BAD_REQUEST);
	}
}
