package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CommentNotFoundException extends ApplicationException {
	public CommentNotFoundException() {
		super(ErrorCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
}
