package com.unionmate.backend.domain.applicant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "DecisionRequest", description = "평가 결과 결정 요청")
public record DecisionRequest(

	@Schema(description = "평가 결과", example = "PASSED", allowableValues = {"PASSED", "FAILED"})
	@NotNull Decision decision
) {
	public enum Decision {PASSED, FAILED}
}
