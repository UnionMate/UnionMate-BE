package com.unionmate.backend.domain.member.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class SchoolNotFoundException extends ApplicationException {
	public SchoolNotFoundException() {
		super(ErrorCode.SCHOOL_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}