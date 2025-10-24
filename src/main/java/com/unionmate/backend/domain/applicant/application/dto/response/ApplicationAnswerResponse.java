package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public sealed interface ApplicationAnswerResponse
	permits TextAnswerResponse, SelectAnswerResponse, CalendarAnswerResponse {
	ItemType itemType();

	String title();

	Integer order();

	String description();
}
