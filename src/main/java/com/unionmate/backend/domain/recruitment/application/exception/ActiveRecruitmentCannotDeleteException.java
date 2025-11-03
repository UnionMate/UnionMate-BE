package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ActiveRecruitmentCannotDeleteException extends ApplicationException {
	public ActiveRecruitmentCannotDeleteException() {
		super(ErrorCode.ACTIVE_RECRUITMENT_CANNOT_DELETE, HttpStatus.FORBIDDEN);
	}
}
