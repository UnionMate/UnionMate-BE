package com.unionmate.backend.domain.applicant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "UpdateInterviewEvaluationRequest", description = "면접 평가 수정 요청")
public record UpdateInterviewEvaluationRequest(
	@Schema(description = "면접 평가 내용(최대 250자)", example = "기술 질문에서 자료구조 이해도가 높았습니다.")
	@NotBlank @Size(max = 1000)
	String evaluation
) {}
