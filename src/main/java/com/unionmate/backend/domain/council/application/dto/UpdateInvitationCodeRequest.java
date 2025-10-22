package com.unionmate.backend.domain.council.application.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateInvitationCodeRequest(
	@NotNull
	String invitationCode
) {
}