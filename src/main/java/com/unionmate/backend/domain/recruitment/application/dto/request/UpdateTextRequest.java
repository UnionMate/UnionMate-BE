package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateTextRequest(
	@NotNull
	@Schema(description = "항목 id", example = "1")
	Long id,

	@Schema(description = "필수 여부", example = "true")
	Boolean required,

	@Schema(description = "항목 이름", example = "지원동기")
	String title,

	@Schema(description = "항목 순서", example = "1")
	Integer order,

	@Schema(description = "항목 설명", example = "지원 동기를 입력해주세요.")
	String description,

	@Schema(description = "최대 글자수", example = "700")
	Integer maxLength
) implements UpdateItemRequest {
}
