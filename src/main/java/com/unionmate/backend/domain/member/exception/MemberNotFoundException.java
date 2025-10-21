package com.unionmate.backend.domain.member.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class MemberNotFoundException extends ApplicationException {
	public MemberNotFoundException() {
		super(ErrorCode.MEMBER_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
