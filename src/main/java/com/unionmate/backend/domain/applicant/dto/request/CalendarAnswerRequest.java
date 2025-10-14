package com.unionmate.backend.domain.applicant.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CalendarAnswerRequest(
	@Schema(description = "CalendarItem의 id", example = "1")
	@NotNull
	Long itemId,

	@Schema(description = "선택한 날짜", example = "2025-12-31")
	@NotNull
	LocalDate date
) implements AnswerRequest {
}
