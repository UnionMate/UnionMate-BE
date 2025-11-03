package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import jakarta.validation.Valid;

public record UpdateRecruitmentRequest(
	String name,
	LocalDateTime endAt,
	Boolean isActive,
	RecruitmentStatus recruitmentStatus,
	List<@Valid CreateItemRequest> items
) {
}
