package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.recruitment.application.exception.ItemTypeNotExistException;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

public sealed interface ApplicationAnswerResponse
	permits TextAnswerResponse, SelectAnswerResponse, CalendarAnswerResponse {
	ItemType itemType();

	String title();

	Integer order();

	String description();

	static ApplicationAnswerResponse from(Item item) {
		return switch (item) {
			case TextItem textItem -> TextAnswerResponse.from(textItem);
			case SelectItem selectItem -> SelectAnswerResponse.from(selectItem);
			case CalendarItem calendarItem -> CalendarAnswerResponse.from(calendarItem);
			default -> throw new ItemTypeNotExistException();
		};
	}
}
