package com.unionmate.backend.domain.applicant.application.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateApplicantRequest(
	@Schema(description = "지원자 이름", example = "김가천")
	@NotBlank
	String name,

	@Schema(description = "지원자 이메일", example = "unionmate@unionmate.com")
	@NotBlank
	String email,

	@Schema(description = "지원자 연락처", example = "010123411234")
	@NotBlank
	String tel,

	@Schema(description = "문항별 답변 목록")
	@NotNull
	List<@Valid AnswerRequest> answers
) {
}
