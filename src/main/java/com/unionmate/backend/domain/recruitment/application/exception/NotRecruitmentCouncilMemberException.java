package com.unionmate.backend.domain.recruitment.application.exception;

import org.springframework.http.HttpStatus;

import com.unionmate.backend.exception.ApplicationException;

public class NotRecruitmentCouncilMemberException extends ApplicationException {
	public NotRecruitmentCouncilMemberException() {
		super(ErrorCode.NOT_RECRUITMENT_COUNCIL_MEMBER, HttpStatus.FORBIDDEN);
	}
}
