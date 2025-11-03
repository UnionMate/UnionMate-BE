package com.unionmate.backend.domain.recruitment.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모집 게시 토글 요청(OFF→ON만 허용). active=false 요청은 거부됩니다.")
public record ToggleRecruitmentActivationRequest(

	@Schema(description = "활성화 여부. true만 허용됩니다.", example = "true")
	boolean active
) {
	public static ToggleRecruitmentActivationRequest of(boolean active) {
		return new ToggleRecruitmentActivationRequest(active);
	}
}
