package com.unionmate.backend.domain.recruitment.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

import io.swagger.v3.oas.annotations.media.Schema;

public record TextResponse(

	@Schema(description = "단답/장문형 답변 id", example = "5")
	Long id,

	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT", example = "TEXT")
	ItemType type,

	@Schema(description = "필수 입력 여부", example = "true")
	boolean required,

	@Schema(description = "항목 제목", example = "지원동기")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "3")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "지원동기에 대해서 간략히 설명해주세요.")
	String description,

	@Schema(description = "최대 글자 수", example = "700")
	Integer maxLength
) implements ItemResponse {
}
