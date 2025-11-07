package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class ActiveRecruitmentCannotChangeException extends ApplicationException {
	public ActiveRecruitmentCannotChangeException() {
		super(ErrorCode.ACTIVE_RECRUITMENT_CANNOT_CHANGE, HttpStatus.FORBIDDEN);
	}
}
