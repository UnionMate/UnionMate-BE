package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectResponse(
	@Schema(description = "선택지 id", example = "4")
	Long id,

	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT", example = "SELECT")
	ItemType type,

	@Schema(description = "필수 입력 여부", example = "true")
	boolean required,

	@Schema(description = "항목 제목", example = "가능한 요일 선택")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "가능한 면접 날짜를 선택해주세요.")
	String description,

	@Schema(description = "단일/복수 선택 여부", example = "true")
	boolean multiple,

	List<SelectOptionResponse> options
) implements ItemResponse {
}
