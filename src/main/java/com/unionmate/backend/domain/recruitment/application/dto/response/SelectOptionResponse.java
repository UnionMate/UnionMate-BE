package com.unionmate.backend.domain.recruitment.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectOptionResponse(
	@Schema(description = "선택 항목 id", example = "3")
	Long id,

	@Schema(description = "선택 항목 이름", example = "월요일")
	String title,

	@Schema(description = "선택 항목 순서", example = "1")
	Integer order,

	@Schema(description = "기타 항목 여부", example = "false")
	Boolean isEtc,

	@Schema(description = "기타 항목 이름", example = "null")
	String etcTitle
) {
}
