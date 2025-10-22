package com.unionmate.backend.domain.council.application.dto;

import com.unionmate.backend.domain.council.domain.entity.Council;

public record UpdateInvitationCodeResponse(
	long councilId,
	String invitationCode
) {
	public static UpdateInvitationCodeResponse from(Council council) {
		return new UpdateInvitationCodeResponse(
			council.getId(),
			council.getInvitationCode()
		);
	}
}