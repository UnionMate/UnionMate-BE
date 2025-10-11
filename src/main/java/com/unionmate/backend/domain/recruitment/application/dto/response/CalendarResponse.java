package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDate;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

import io.swagger.v3.oas.annotations.media.Schema;

public record CalendarResponse(
	@Schema(description = "날짜 id", example = "2")
	Long id,

	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT)", example = "CALENDAR")
	ItemType type,

	@Schema(description = "필수 입력 여부", example = "true")
	boolean required,

	@Schema(description = "항목 제목", example = "날짜 선택")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "해당 날짜 전까지 희망하는 날짜를 작성해주세요")
	String description,

	@Schema(description = "날짜 지정", example = "2025-12-31")
	LocalDate date
) implements ItemResponse {
}
