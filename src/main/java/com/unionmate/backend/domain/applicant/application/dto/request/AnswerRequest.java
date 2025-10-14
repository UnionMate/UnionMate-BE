package com.unionmate.backend.domain.applicant.application.dto.request;

import jakarta.validation.constraints.NotNull;

public sealed interface AnswerRequest permits TextAnswerRequest, SelectAnswerRequest, CalendarAnswerRequest {
	@NotNull
	Long itemId();
}
