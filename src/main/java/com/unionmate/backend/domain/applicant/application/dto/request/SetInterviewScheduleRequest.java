package com.unionmate.backend.domain.applicant.application.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SetInterviewScheduleRequest(
	@NotNull(message = "면접 일시는 필수입니다.")
	LocalDateTime time,

	@NotBlank(message = "면접 장소는 필수입니다.")
	String place
) {
}