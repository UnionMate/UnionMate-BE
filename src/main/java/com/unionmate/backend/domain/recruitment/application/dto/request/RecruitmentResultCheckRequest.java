package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "이름 + 이메일로 결과 단건 조회 요청 DTO")
public record RecruitmentResultCheckRequest(

	@Schema(description = "지원자 이름", example = "이지훈")
	@NotBlank String applicantName,

	@Schema(description = "지원자 이메일", example = "huncozyboy@gachon.ac.kr")
	@NotBlank String email
) {
}
