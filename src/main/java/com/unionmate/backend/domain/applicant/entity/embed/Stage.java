package com.unionmate.backend.domain.applicant.entity.embed;

import com.unionmate.backend.domain.applicant.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.recruitment.entity.enums.RecruitmentStatus;
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

  public static Stage init() {
    return new Stage(RecruitmentStatus.DOCUMENT_SCREENING, EvaluationStatus.SUBMITTED);
  }
}
