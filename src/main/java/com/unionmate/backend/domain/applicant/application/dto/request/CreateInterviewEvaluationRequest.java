package com.unionmate.backend.domain.applicant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CreateInterviewEvaluationRequest", description = "면접 평가 생성 요청")
public record CreateInterviewEvaluationRequest(

	@Schema(description = "면접 평가 내용(최대 250)", example = "의사소통 능력과 발표력이 우수합니다.")
	@NotBlank @Size(max = 250)
	String evaluation
) {}