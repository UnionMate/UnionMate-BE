package com.unionmate.backend.domain.council.application.dto;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public record CouncilApplicantQueryRow(
	String name,
	String email,
	String tel,
	LocalDateTime appliedAt,
	EvaluationStatus evaluationStatus,
	RecruitmentStatus recruitmentStatus
) {
	public static CouncilApplicantQueryRow of(
		String name, String email, String tel, LocalDateTime appliedAt, EvaluationStatus evaluationStatus,
		RecruitmentStatus recruitmentStatus
	) {
		return new CouncilApplicantQueryRow(name, email, tel, appliedAt, evaluationStatus, recruitmentStatus);
	}
}
