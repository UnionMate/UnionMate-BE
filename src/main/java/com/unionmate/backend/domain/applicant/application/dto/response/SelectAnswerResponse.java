package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;

public record SelectAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	boolean multiple,
	List<Long> selectedOptionIds
) implements ApplicationAnswerResponse {

	public static SelectAnswerResponse from(SelectItem selectItem) {
		return new SelectAnswerResponse(
			ItemType.TEXT,
			selectItem.getTitle(),
			selectItem.getOrder(),
			selectItem.getDescription(),
			selectItem.isMultiple(),
			selectItem.getAnswer() == null ? List.of() : selectItem.getAnswer().answer()
		);
	}
}
