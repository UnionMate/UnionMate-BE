package com.unionmate.backend.domain.applicant.application.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

public record UpdateApplicationRequest(
	@Schema(description = "지원자 이름", example = "김가천")
	String name,

	@Schema(description = "지원자 이메일", example = "unionmate@unionmate.com")
	String email,

	@Schema(description = "지원자 연락처", example = "010123411234")
	String tel,

	@Schema(description = "문항별 답변 목록")
	List<@Valid AnswerRequest> answers
) {
}
