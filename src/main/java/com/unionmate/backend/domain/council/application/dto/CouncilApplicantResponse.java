package com.unionmate.backend.domain.council.application.dto;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CouncilApplicantResponse", description = "학생회 지원자 정보")
public record CouncilApplicantResponse(
	@Schema(description = "지원자 이름", example = "이지훈")
	String name,

	@Schema(description = "지원자 이메일", example = "unionmate@unionmate.com")
	String email,

	@Schema(description = "지원자 전화번호", example = "010-6666-6666")
	String tel,

	@Schema(description = "지원 일자(생성 일시)", example = "2025-10-30T20:15:42")
	LocalDateTime appliedAt,

	@Schema(description = "평가 상태", example = "FAILED", allowableValues = {"SUBMITTED", "FAILED", "PASSED"})
	EvaluationStatus evaluationStatus
) {
	public static CouncilApplicantResponse of(
		String name, String email, String tel, LocalDateTime appliedAt, EvaluationStatus evaluationStatus
	) {
		return new CouncilApplicantResponse(name, email, tel, appliedAt, evaluationStatus);
	}
}
