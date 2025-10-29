package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CommentForbiddenException extends ApplicationException {
	public CommentForbiddenException() {
		super(ErrorCode.COMMENT_FORBIDDEN, HttpStatus.FORBIDDEN);
	}
}
