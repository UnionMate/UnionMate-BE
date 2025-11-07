package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateSelectOptionRequest(
	@Schema(description = "선택지 id", example = "1")
	Long id,

	@Schema(description = "선택지 이름", example = "월요일")
	String title,

	@Schema(description = "선택지 순서", example = "1")
	Integer order,

	@Schema(description = "기타 여부", example = "true")
	Boolean isEtc,

	@Schema(description = "기타 항목 제목", example = "기타(입력해주세요.)")
	String etcTitle
) {
}
