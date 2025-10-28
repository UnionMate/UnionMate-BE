package com.unionmate.backend.domain.applicant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record GetMyApplicationsRequest(
	@Schema(description = "지원자 이름", example = "김가천")
	@NotBlank
	String name,

	@Schema(description = "지원자 이메일", example = "unionmate@unionmate.com")
	@NotBlank
	String email
) {
}
