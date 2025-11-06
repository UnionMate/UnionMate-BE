package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public record GetRecruitmentsResponse(
	Long id,
	String name,
	Boolean isActive,
	RecruitmentStatus recruitmentStatus
) {
	public static GetRecruitmentsResponse from(Recruitment recruitment) {
		return new GetRecruitmentsResponse(
			recruitment.getId(),
			recruitment.getName(),
			recruitment.getIsActive(),
			recruitment.getRecruitmentStatus()
		);
	}
}
