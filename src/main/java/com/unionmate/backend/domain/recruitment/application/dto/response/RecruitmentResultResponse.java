package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.time.LocalDateTime;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.embed.Interview;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이름+이메일 결과 단건 응답 DTO")
public record RecruitmentResultResponse(

	@Schema(description = "지원자 정보")
	ApplicantInfo applicant,

	@Schema(description = "학생회 이름", example = "컴퓨터공학과 학생회")
	String councilName,

	@Schema(description = "학생회 관리자의 이메일 주소", example = "leader@gachon.ac.kr")
	String councilManagerEmail,

	@Schema(description = "모집 공고 정보")
	RecruitmentBrief recruitment,

	@Schema(description = "면접 정보")
	InterviewResponse interview,

	@Schema(description = "지원 단계")
	StageResponse stage
) {
	public static RecruitmentResultResponse from(Application application, String councilManagerEmail) {
		Interview interview = application.getInterview();
		return new RecruitmentResultResponse(
			new ApplicantInfo(application.getName(), application.getEmail(), application.getTel()),
			application.getRecruitment().getCouncil().getName(),
			councilManagerEmail,
			new RecruitmentBrief(
				application.getRecruitment().getId(),
				application.getRecruitment().getName()
			),
			from(interview),
			new StageResponse(
				application.getStage().recruitmentStatus(),
				application.getStage().evaluationStatus()
			)
		);
	}

	private static RecruitmentResultResponse.InterviewResponse from(Interview interview) {
		if (interview == null) {
			return null;
		}
		return new RecruitmentResultResponse.InterviewResponse(interview.time(), interview.place());
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

	@Schema(description = "모집 공고 요약")
	public record RecruitmentBrief(
		@Schema(description = "모집 공고 ID", example = "77")
		Long recruitmentId,
		@Schema(description = "모집 공고명", example = "리츠 1기")
		String recruitmentName
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

	@Schema(description = "진행 단계")
	public record StageResponse(
		@Schema(description = "리크루팅 단계", example = "DOCUMENT_SCREENING")
		RecruitmentStatus recruitmentStatus,
		@Schema(description = "평가 상태", example = "SUBMITTED")
		EvaluationStatus evaluationStatus
	) {
	}
}