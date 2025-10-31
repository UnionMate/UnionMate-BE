package com.unionmate.backend.domain.applicant.domain.entity.embed;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record Stage(
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	RecruitmentStatus recruitmentStatus,

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	EvaluationStatus evaluationStatus
) {
	// 서류 합격 → 인터뷰 단계 대기
	public static Stage documentDecisionPassed() {
		return new Stage(RecruitmentStatus.INTERVIEW, EvaluationStatus.SUBMITTED);
	}

	// 서류 탈락 → 서류 단계에서 종료
	public static Stage documentDecisionFailed() {
		return new Stage(RecruitmentStatus.DOCUMENT_SCREENING, EvaluationStatus.FAILED);
	}

	// 면접 합격 → 최종 단계 확정
	public static Stage finalizePassed() {
		return new Stage(RecruitmentStatus.FINAL, EvaluationStatus.PASSED);
	}

	// 면접 탈락 → 최종 단계 확정
	public static Stage finalizeFailed() {
		return new Stage(RecruitmentStatus.FINAL, EvaluationStatus.FAILED);
	}

	public static Stage init() {
		return new Stage(RecruitmentStatus.DOCUMENT_SCREENING, EvaluationStatus.SUBMITTED);
	}

	public Stage failOnThisStage() {
		return new Stage(this.recruitmentStatus, EvaluationStatus.FAILED);
	}

	public Stage passOnThisStage() {
		return new Stage(this.recruitmentStatus, EvaluationStatus.PASSED);
	}

	public Stage toNextStage() {
		switch (this.recruitmentStatus) {
			case DOCUMENT_SCREENING -> {
				return new Stage(RecruitmentStatus.INTERVIEW, EvaluationStatus.SUBMITTED);
			}
			case INTERVIEW -> {
				return new Stage(RecruitmentStatus.FINAL, EvaluationStatus.SUBMITTED);
			}
		}
		// TODO: 예외 처리 로직 확정되면 예외 처리
		throw new IllegalStateException();
	}
}
