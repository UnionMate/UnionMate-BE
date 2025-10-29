package com.unionmate.backend.domain.council.application.dto;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;

public record CouncilApplicantRow(
	String name,
	String email,
	String tel,
	LocalDateTime appliedAt,
	EvaluationStatus evaluationStatus
) {
	public static CouncilApplicantRow of(
		String name, String email, String tel, LocalDateTime appliedAt, EvaluationStatus evaluationStatus
	) {
		return new CouncilApplicantRow(name, email, tel, appliedAt, evaluationStatus);
	}
}
