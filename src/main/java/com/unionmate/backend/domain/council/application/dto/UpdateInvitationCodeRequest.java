package com.unionmate.backend.domain.council.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateInvitationCodeRequest(
	@NotBlank
	String invitationCode
) {
}
