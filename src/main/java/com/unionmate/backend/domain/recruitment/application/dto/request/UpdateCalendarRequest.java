package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record UpdateCalendarRequest(
	@NotNull
	Long id,
	Boolean required,
	String title,
	Integer order,
	String description,
	LocalDate date
) implements UpdateItemRequest{
}
