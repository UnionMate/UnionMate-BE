package com.unionmate.backend.domain.recruitment.application.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = UpdateTextRequest.class, name = "TEXT"),
	@JsonSubTypes.Type(value = UpdateSelectRequest.class, name = "SELECT"),
	@JsonSubTypes.Type(value = UpdateCalendarRequest.class, name = "CALENDAR"),
	@JsonSubTypes.Type(value = UpdateAnnouncementRequest.class, name = "ANNOUNCEMENT")
})
public sealed interface UpdateItemRequest permits UpdateTextRequest, UpdateSelectRequest, UpdateCalendarRequest, UpdateAnnouncementRequest {
	@NotNull
	Long id();

	Boolean required();

	String title();

	Integer order();

	String description();
}
