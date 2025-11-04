package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모집 게시 상태 토글 요청(ON/OFF 모두 허용)")
public record ToggleRecruitmentActivationRequest(

	@Schema(description = "활성화 여부 (모집 게시 여부)", example = "true")
	boolean active
) {
}
