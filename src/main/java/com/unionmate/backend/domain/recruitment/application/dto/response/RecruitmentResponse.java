package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecruitmentResponse(
	@Schema(description = "지원서 id", example = "1")
	Long id,

	@Schema(description = "지원서 이름", example = "2025 가천대학교 컴퓨터공학과 학생회 부원 모집")
	String name,

	@Schema(description = "지원 시작 날짜", example = "2025-10-31T23:59:00")
	LocalDateTime startAt,

	@Schema(description = "지원 마감 날짜", example = "2025-11-14T23:59:00")
	LocalDateTime endAt,

	@Schema(description = "현재 지원 가능 여부", example = "true")
	Boolean isActive,

	@Schema(description = "지원서 진행 상태(DOCUMENT_SCREENING, INTERVIEW, FINAL", example = "FINAL")
	RecruitmentStatus recruitmentStatus,

	@Schema(description = "지원서에 들어갈 항목들 목록")
	List<ItemResponse> items
) {
}
