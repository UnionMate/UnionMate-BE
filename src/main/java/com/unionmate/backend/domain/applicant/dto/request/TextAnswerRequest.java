package com.unionmate.backend.domain.applicant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TextAnswerRequest(
	@Schema(description = "TextItem의 id", example = "1")
	@NotNull
	Long itemId,

	@Schema(description = "텍스트 답변", example = "지원동기는 저의 실력을 올리기 위해서입니다.")
	String text
) implements AnswerRequest {
}
