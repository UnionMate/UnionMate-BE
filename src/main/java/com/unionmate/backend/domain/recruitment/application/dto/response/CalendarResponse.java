package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDate;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record CalendarResponse(
	Long id,
	ItemType type,
	boolean required,
	String title,
	Integer order,
	String description,
	LocalDate date
) implements ItemResponse {
}
