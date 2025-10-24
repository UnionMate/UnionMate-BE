package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

public record TextAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	Integer maxLength,
	String answer
) implements ApplicationAnswerResponse {

	public static TextAnswerResponse from(TextItem textItem) {
		return new TextAnswerResponse(
			ItemType.TEXT,
			textItem.getTitle(),
			textItem.getOrder(),
			textItem.getDescription(),
			textItem.getMaxLength(),
			textItem.getAnswer() == null ? null : textItem.getAnswer().answer()
		);
	}
}
