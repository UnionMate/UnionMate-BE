package com.unionmate.backend.domain.applicant.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class CommentApplicationMismatchException extends ApplicationException {
	public CommentApplicationMismatchException() {
		super(ErrorCode.COMMENT_APPLICATION_MISMATCH, HttpStatus.BAD_REQUEST);
	}
}
