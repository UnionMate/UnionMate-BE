package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "모집 게시 토글 응답")
public record ToggleRecruitmentActivationResponse(

	@Schema(description = "모집 ID", example = "101")
	Long recruitmentId,

	@Schema(description = "현재 활성화 여부", example = "true")
	boolean active,

	@Schema(description = "모집 시간 내부에 공개 했는지 여부 (startAt ~ endAt)", example = "true")
	boolean open,

	@Schema(description = "모집 시작 시각")
	LocalDateTime startAt,

	@Schema(description = "모집 종료 시각")
	LocalDateTime endAt
) {
	public static ToggleRecruitmentActivationResponse of(
		Long recruitmentId, boolean active, boolean open, LocalDateTime startAt, LocalDateTime endAt
	) {
		return new ToggleRecruitmentActivationResponse(recruitmentId, active, open, startAt, endAt);
	}
}
