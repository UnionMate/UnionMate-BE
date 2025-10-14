package com.unionmate.backend.domain.applicant.dto.request;

import jakarta.validation.constraints.NotNull;

public sealed interface AnswerRequest permits TextAnswerRequest, SelectAnswerRequest, CalendarAnswerRequest {
	@NotNull
	Long itemId();
}
