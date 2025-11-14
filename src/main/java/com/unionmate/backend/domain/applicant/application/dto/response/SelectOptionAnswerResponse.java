package com.unionmate.backend.domain.applicant.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectOptionAnswerResponse(
	@Schema(description = "선택지 id", example = "1")
	Long optionId,

	@Schema(description = "선택지 title", example = "월요일")
	String title
) {
}
