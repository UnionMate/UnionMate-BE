package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InterviewEvaluationResponse", description = "면접 평가 코멘트 응답 DTO")
public record InterviewEvaluationResponse(
	@Schema(description = "면접 평가 ID", example = "100")
	Long evaluationId,

	@Schema(description = "작성자(임원) ID", example = "42")
	Long councilManagerId,

	@Schema(description = "작성자 이름", example = "홍길동")
	String councilManagerName,

	@Schema(description = "면접 평가 내용", example = "의사소통 능력이 우수합니다.")
	String evaluation,

	@Schema(description = "작성일시", example = "2025-01-22T12:30:00")
	LocalDateTime createdAt,

	@Schema(description = "수정일시", example = "2025-01-23T09:15:00")
	LocalDateTime updatedAt
) {
	public static InterviewEvaluationResponse from(InterviewEvaluation interviewEvaluation) {
		String writerName = interviewEvaluation.getCouncilManager().getMember().getName();
		return new InterviewEvaluationResponse(
			interviewEvaluation.getId(),
			interviewEvaluation.getCouncilManager().getId(),
			writerName,
			interviewEvaluation.getEvaluation(),
			interviewEvaluation.getCreatedAt(),
			interviewEvaluation.getUpdatedAt()
		);
	}

	public static List<InterviewEvaluationResponse> fromList(List<InterviewEvaluation> list) {
		return list.stream().map(InterviewEvaluationResponse::from).toList();
	}
}
