package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public record RecruitmentResponse(
	Long id,
	String name,
	LocalDateTime startAt,
	LocalDateTime endAt,
	Boolean isActive,
	RecruitmentStatus recruitmentStatus,
	List<ItemResponse> items
) {
}
