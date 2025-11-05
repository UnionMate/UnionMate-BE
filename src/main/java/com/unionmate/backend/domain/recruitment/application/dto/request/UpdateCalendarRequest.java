package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateCalendarRequest(
	@NotNull
	@Schema(description = "항목 id", example = "1")
	Long id,

	@Schema(description = "필수 여부", example = "true")
	Boolean required,

	@Schema(description = "항목 이름", example = "가능한 날짜")
	String title,

	@Schema(description = "항목 순서", example = "1")
	Integer order,

	@Schema(description = "항목 설명", example = "희망하는 날짜를 선택해주세요")
	String description,

	@Schema(description = "날짜", example = "2025-11-30")
	LocalDate date
) implements UpdateItemRequest{
}
