package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record TextAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	String text
) implements ApplicationAnswerResponse {
}
