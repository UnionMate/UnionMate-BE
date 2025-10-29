package com.unionmate.backend.domain.applicant.application.usecase;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.InterviewDecisionRequest;
import com.unionmate.backend.domain.applicant.application.exception.ApplicationEvaluationForbiddenException;
import com.unionmate.backend.domain.applicant.application.exception.ApplicationEvaluationInvalidStageException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.embed.Stage;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationSaveService;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationDecisionUseCase {

	private final ApplicationGetService applicationGetService;
	private final ApplicationSaveService applicationSaveService;
	private final CouncilManagerGetService councilManagerGetService;

	@Transactional
	public void failOnDocument(Long memberId, Long applicationId) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		validateSameCouncil(councilManager, application);
		validateDocumentSubmitted(application);

		application.updateStage(Stage.finalizeFailed());
		applicationSaveService.save(application);
	}

	@Transactional
	public void decideOnInterview(Long memberId, Long applicationId, InterviewDecisionRequest request) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		validateSameCouncil(councilManager, application);
		validateInterviewSubmitted(application);

		if (request.decision() == InterviewDecisionRequest.Decision.PASSED) {
			application.updateStage(Stage.finalizePassed());
		} else {
			application.updateStage(Stage.finalizeFailed());
		}

		applicationSaveService.save(application);
	}

	private void validateSameCouncil(CouncilManager councilManager, Application application) {
		Long managerCouncilId = councilManager.getCouncil().getId();
		Long applicationCouncilId = application.getRecruitment().getCouncil().getId();
		if (!Objects.equals(managerCouncilId, applicationCouncilId)) {
			throw new ApplicationEvaluationForbiddenException();
		}
	}

	private void validateDocumentSubmitted(Application application) {
		Stage stage = application.getStage();
		boolean ok = stage.recruitmentStatus() == RecruitmentStatus.DOCUMENT_SCREENING
			&& stage.evaluationStatus() == EvaluationStatus.SUBMITTED;
		if (!ok)
			throw new ApplicationEvaluationInvalidStageException();
	}

	private void validateInterviewSubmitted(Application application) {
		Stage stage = application.getStage();
		boolean ok = stage.recruitmentStatus() == RecruitmentStatus.INTERVIEW
			&& stage.evaluationStatus() == EvaluationStatus.SUBMITTED;
		if (!ok)
			throw new ApplicationEvaluationInvalidStageException();
	}
}
