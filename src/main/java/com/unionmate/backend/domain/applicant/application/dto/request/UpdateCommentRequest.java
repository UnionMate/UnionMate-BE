package com.unionmate.backend.domain.applicant.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(

	@Schema(description = "지원서 평가 내용", example = "특색이 없음")
	@NotBlank @Size(max = 250)
	String content
) {}
