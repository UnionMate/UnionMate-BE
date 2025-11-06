package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public record GetRecruitmentsResponse(
	Long id,
	String name,
	Boolean isActive,
	RecruitmentStatus recruitmentStatus,
	boolean isOpen
) {
	public static GetRecruitmentsResponse from(Recruitment recruitment, LocalDateTime now) {
		return new GetRecruitmentsResponse(
			recruitment.getId(),
			recruitment.getName(),
			recruitment.getIsActive(),
			recruitment.getRecruitmentStatus(),
			recruitment.isOpen(now)
		);
	}
}
