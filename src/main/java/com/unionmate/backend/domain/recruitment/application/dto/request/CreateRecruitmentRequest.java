package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRecruitmentRequest(
	@Schema(description = "지원서 이름", example = "2025 가천대학교 컴퓨터공학과 학생회 부원 모집")
	String name,

	@Schema(description = "지원서 마감 시간", example = "2025-10-31T23:59:00")
	LocalDateTime endAt,

	@Schema(description = "지원서 마감 여부", example = "true")
	Boolean isActive,

	@Schema(description = "지원서 진행 상태(DOCUMENT_SCREENING, INTERVIEW, FINAL", example = "FINAL")
	RecruitmentStatus recruitmentStatus,

	List<CreateItemRequest> items
) {
}
