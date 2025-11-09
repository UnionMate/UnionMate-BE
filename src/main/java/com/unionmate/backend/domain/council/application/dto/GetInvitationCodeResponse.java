package com.unionmate.backend.domain.council.application.dto;

import com.unionmate.backend.domain.council.domain.entity.Council;

public record GetInvitationCodeResponse(
	long councilId,
	String invitationCode
) {
	public static GetInvitationCodeResponse from(Council council) {
		return new GetInvitationCodeResponse(
			council.getId(),
			council.getInvitationCode()
		);
	}
}