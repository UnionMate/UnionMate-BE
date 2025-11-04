package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관리자용 지원서 상세 응답")
public record GetApplicationAdminResponse(

	@Schema(description = "지원서 ID", example = "123")
	Long applicationId,

	@Schema(description = "모집 공고 정보")
	RecruitmentBrief recruitment,

	@Schema(description = "지원자 정보")
	ApplicantInfo applicant,

	@Schema(description = "지원 단계")
	StageResponse stage,

	@Schema(description = "면접 정보")
	InterviewResponse interview,

	@Schema(description = "문항별 답변 목록(문항 order 오름차순)")
	List<ApplicationAnswerResponse> answers,

	@Schema(description = "지원서 제출 시각", example = "2025-02-05T09:10:00")
	LocalDateTime submittedAt
) {
	public static GetApplicationAdminResponse from(Application application) {
		List<ApplicationAnswerResponse> sortedAnswers = application.getAnswers().stream()
			.sorted(Comparator.comparing(Item::getOrder))
			.map(ApplicationAnswerResponse::from)
			.toList();

		return new GetApplicationAdminResponse(
			application.getId(),
			new RecruitmentBrief(
				application.getRecruitment().getId(),
				application.getRecruitment().getName()
			),
			new ApplicantInfo(
				application.getName(),
				application.getEmail(),
				application.getTel()
			),
			new StageResponse(
				application.getStage().recruitmentStatus(),
				application.getStage().evaluationStatus()
			),
			new InterviewResponse(
				application.getInterview().time(),
				application.getInterview().place()
			),
			sortedAnswers,
			application.getCreatedAt()
		);
	}

	@Schema(description = "모집 공고 요약")
	public record RecruitmentBrief(

		@Schema(description = "모집 공고 ID", example = "77")
		Long recruitmentId,

		@Schema(description = "모집 공고명", example = "리츠 1기")
		String recruitmentName
	) {
	}

	@Schema(description = "지원자 기본 정보")
	public record ApplicantInfo(

		@Schema(description = "이름", example = "이지훈")
		String name,

		@Schema(description = "이메일", example = "huncozyboy@example.com")
		String email,

		@Schema(description = "연락처", example = "010-1234-5678")
		String tel
	) {
	}

	@Schema(description = "진행 단계")
	public record StageResponse(

		@Schema(description = "리크루팅 단계", example = "DOCUMENT_SCREENING")
		RecruitmentStatus recruitmentStatus,

		@Schema(description = "평가 상태", example = "SUBMITTED")
		EvaluationStatus evaluationStatus
	) {
	}

	@Schema(description = "면접 정보")
	public record InterviewResponse(

		@Schema(description = "면접 일시", example = "2025-02-10T14:00:00")
		LocalDateTime time,

		@Schema(description = "면접 장소", example = "판교 플레이그라운드 5층 B-회의실")
		String place
	) {
	}
}
