package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public record GetMyApplicationsResponse(
	Long applicationId,
	Long recruitmentId,
	String recruitmentName,
	RecruitmentStatus recruitmentStatus
) {
	public static GetMyApplicationsResponse from(Application application) {
		return new GetMyApplicationsResponse(
			application.getId(),
			application.getRecruitment().getId(),
			application.getRecruitment().getName(),
			application.getStage().recruitmentStatus()
		);
	}
}
