package com.unionmate.backend.domain.council.application.dto;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;

public record CouncilApplicantQueryRow(
	String name,
	String email,
	String tel,
	LocalDateTime appliedAt,
	EvaluationStatus evaluationStatus
) {
	public static CouncilApplicantQueryRow of(
		String name, String email, String tel, LocalDateTime appliedAt, EvaluationStatus evaluationStatus
	) {
		return new CouncilApplicantQueryRow(name, email, tel, appliedAt, evaluationStatus);
	}
}
