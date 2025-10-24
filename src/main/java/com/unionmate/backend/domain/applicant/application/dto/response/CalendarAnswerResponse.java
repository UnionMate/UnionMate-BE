package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDate;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record CalendarAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	LocalDate date
) implements ApplicationAnswerResponse {
}
