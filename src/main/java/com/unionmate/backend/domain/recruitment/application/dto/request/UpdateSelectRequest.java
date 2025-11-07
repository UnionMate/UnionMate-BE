package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateSelectRequest(
	@NotNull
	@Schema(description = "항목 id", example = "1")
	Long id,

	@Schema(description = "필수 여부", example = "true")
	Boolean required,

	@Schema(description = "항목 이름", example = "면접 가능 날짜 선택")
	String title,

	@Schema(description = "항목 순서", example = "1")
	Integer order,

	@Schema(description = "항목 설명", example = "가능한 날짜를 모두 선택해 주세요.")
	String description,

	@Schema(description = "중복 선택 여부", example = "true")
	Boolean multiple,

	@Schema(description = "제거할 선택지")
	List<Long> removeOptions,

	@Schema(description = "수정/생성할 선택지")
	List<@Valid UpdateSelectOptionRequest> updateOptions
) implements UpdateItemRequest {
}
