package com.unionmate.backend.domain.recruitment.application.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
	@JsonSubTypes.Type(value = UpdateTextRequest.class),
	@JsonSubTypes.Type(value = UpdateSelectRequest.class),
	@JsonSubTypes.Type(value = UpdateCalendarRequest.class),
	@JsonSubTypes.Type(value = UpdateAnnouncementRequest.class)
})
public sealed interface UpdateItemRequest permits UpdateTextRequest, UpdateSelectRequest, UpdateCalendarRequest, UpdateAnnouncementRequest {
	@NotNull
	Long id();

	Boolean required();

	String title();

	Integer order();

	String description();
}
