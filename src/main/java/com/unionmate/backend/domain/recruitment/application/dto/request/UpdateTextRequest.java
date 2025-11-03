package com.unionmate.backend.domain.recruitment.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateTextRequest(
	@NotNull
	Long id,
	Boolean required,
	String title,
	Integer order,
	String description,
	Integer maxLength
) implements UpdateItemRequest {
}
