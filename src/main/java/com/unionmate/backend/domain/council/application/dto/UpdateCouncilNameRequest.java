package com.unionmate.backend.domain.council.application.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateCouncilNameRequest(
	@NotNull
	String name
) {
}
