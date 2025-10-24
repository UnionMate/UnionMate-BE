package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record SelectAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	boolean multiple,
	List<Long> selectedOptionIds
) implements ApplicationAnswerResponse {
}
