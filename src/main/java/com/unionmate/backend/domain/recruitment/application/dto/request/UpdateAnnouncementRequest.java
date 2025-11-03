package com.unionmate.backend.domain.recruitment.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateAnnouncementRequest(
	@NotNull
	Long id,
	Boolean required,
	String title,
	Integer order,
	String description,
	String announcement
) implements UpdateItemRequest {
}
