package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

public record UpdateRecruitmentRequest(
	@Schema(description = "지원서 양식 이름", example = "2025 가천대학교 컴퓨터공학과 학생회 모집")
	String name,

	@Schema(description = "지원 마감 시간", example = "2025-10-31T23:59:00")
	LocalDateTime endAt,

	@Schema(description = "현재 지원 가능 여부", example = "true")
	Boolean isActive,

	@Schema(description = "지원서 진행 상태(DOCUMENT_SCREENING, INTERVIEW, FINAL", example = "FINAL")
	RecruitmentStatus recruitmentStatus,

	@Schema(description = "추가할 항목")
	List<@Valid CreateItemRequest> addItems,

	@Schema(description = "수정할 항목")
	List<@Valid UpdateItemRequest> updateItems,

	@Schema(description = "제거할 항목")
	List<Long> removeItems
) {
}
