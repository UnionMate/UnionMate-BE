package com.unionmate.backend.domain.applicant.application.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SelectAnswerRequest(
	@Schema(description = "SelectItem의 id", example = "1")
	@NotNull
	Long itemId,

	@Schema(description = "선택할 선택지의 id 목록(단일 선택이라면 하나만 보내기)")
	@NotNull
	List<Long> optionIds
) implements AnswerRequest {
}
