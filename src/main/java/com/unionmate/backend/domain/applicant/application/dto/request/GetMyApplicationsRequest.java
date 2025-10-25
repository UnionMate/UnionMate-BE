package com.unionmate.backend.domain.applicant.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GetMyApplicationsRequest(
	@NotBlank
	String name,

	@NotBlank
	String email
) {
}
