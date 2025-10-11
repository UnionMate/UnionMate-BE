package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ItemTypeNotExistException extends ApplicationException {
	public ItemTypeNotExistException() {
		super(ErrorCode.ITEM_TYPE_NOT_EXIST, HttpStatus.NOT_FOUND);
	}
}
