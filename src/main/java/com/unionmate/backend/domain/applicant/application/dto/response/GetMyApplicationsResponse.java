package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetMyApplicationsResponse(
	@Schema(description = "지원서 id", example = "1")
	Long applicationId,

	@Schema(description = "지원서 양식 id", example = "1")
	Long recruitmentId,

	@Schema(description = "지원서 이름", example = "25대 컴퓨터공학과 학생회 모집")
	String recruitmentName,

	@Schema(description = "지원서 상태(DOCUMENT_SCREENING, INTERVIEW, FINAL", example = "DOCUMENT_SCREENING")
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
