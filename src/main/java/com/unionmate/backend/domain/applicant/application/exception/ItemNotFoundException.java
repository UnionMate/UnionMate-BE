package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ItemNotFoundException extends ApplicationException {
	public ItemNotFoundException() {
		super(ErrorCode.ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
