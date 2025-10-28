package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDate;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;

import io.swagger.v3.oas.annotations.media.Schema;

public record CalendarAnswerResponse(
	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT)", example = "CALENDAR")
	ItemType itemType,

	@Schema(description = "항목 제목", example = "날짜 선택")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "해당 날짜 전까지 희망하는 날짜를 작성해주세요")
	String description,

	@Schema(description = "날짜 지정", example = "2025-12-31")
	LocalDate date,

	@Schema(description = "답변 날짜")
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
