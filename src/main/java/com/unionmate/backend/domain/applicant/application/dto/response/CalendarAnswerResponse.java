package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDate;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;

public record CalendarAnswerResponse(
	ItemType itemType,
	String title,
	Integer order,
	String description,
	LocalDate date,
	LocalDate answer
) implements ApplicationAnswerResponse {

	public static CalendarAnswerResponse from(CalendarItem calendarItem) {
		return new CalendarAnswerResponse(
			ItemType.CALENDAR,
			calendarItem.getTitle(),
			calendarItem.getOrder(),
			calendarItem.getDescription(),
			calendarItem.getDate(),
			calendarItem.getAnswer() == null ? null : calendarItem.getAnswer().answer()
		);
	}
}
