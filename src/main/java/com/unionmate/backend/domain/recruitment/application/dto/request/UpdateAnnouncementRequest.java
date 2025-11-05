package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateAnnouncementRequest(
	@NotNull
	@Schema(description = "항목 id", example = "1")
	Long id,

	@Schema(description = "필수 여부", example = "true")
	Boolean required,

	@Schema(description = "항목 이름", example = "공지")
	String title,

	@Schema(description = "항목 순서", example = "1")
	Integer order,

	@Schema(description = "항목 설명", example = "주의사항 공지")
	String description,

	@Schema(description = "공지", example = "반드시 컴퓨터공학과 학생만 지원 가능합니다.")
	String announcement
) implements UpdateItemRequest {
}
