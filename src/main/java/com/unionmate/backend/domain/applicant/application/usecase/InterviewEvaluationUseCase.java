package com.unionmate.backend.domain.applicant.application.usecase;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateInterviewEvaluationRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateInterviewEvaluationRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.InterviewEvaluationResponse;
import com.unionmate.backend.domain.applicant.application.exception.CommentForbiddenException;
import com.unionmate.backend.domain.applicant.application.exception.InvalidRecruitmentStatusException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
import com.unionmate.backend.domain.applicant.domain.service.InterviewEvaluationCreateService;
import com.unionmate.backend.domain.applicant.domain.service.InterviewEvaluationDeleteService;
import com.unionmate.backend.domain.applicant.domain.service.InterviewEvaluationGetService;
import com.unionmate.backend.domain.applicant.domain.service.InterviewEvaluationUpdateService;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewEvaluationUseCase {

	private final InterviewEvaluationCreateService interviewEvaluationCreateService;

	private final ApplicationGetService applicationGetService;
	private final CouncilManagerGetService councilManagerGetService;
	private final InterviewEvaluationGetService interviewEvaluationGetService;

	private final InterviewEvaluationUpdateService interviewEvaluationUpdateService;

	private final InterviewEvaluationDeleteService interviewEvaluationDeleteService;

	@Transactional
	public void createEvaluation(Long memberId, Long applicationId, CreateInterviewEvaluationRequest request) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateRecruitmentStatus(application);
		validateSameCouncil(councilManager, application);

		InterviewEvaluation interviewEvaluation = InterviewEvaluation.createInterviewEvaluation(application, councilManager, request.evaluation());
		interviewEvaluationCreateService.create(interviewEvaluation);
	}

	public List<InterviewEvaluationResponse> getEvaluations(Long memberId, Long applicationId) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateRecruitmentStatus(application);
		validateSameCouncil(councilManager, application);

		return InterviewEvaluationResponse.fromList(interviewEvaluationGetService.getAllByApplication(application));
	}

	@Transactional
	public void updateEvaluation(Long memberId, Long applicationId, Long evaluationId, UpdateInterviewEvaluationRequest request) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		interviewEvaluationUpdateService.update(application, councilManager, evaluationId, request.evaluation());
	}

	@Transactional
	public void deleteEvaluation(Long memberId, Long applicationId, Long evaluationId) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateRecruitmentStatus(application);
		validateSameCouncil(councilManager, application);

		InterviewEvaluation evaluation = interviewEvaluationGetService.getByIdAndApplication(evaluationId, application);
		interviewEvaluationDeleteService.delete(evaluation);
	}

	private void validateRecruitmentStatus(Application application) {
		if (application.getRecruitment().getRecruitmentStatus() != RecruitmentStatus.INTERVIEW) {
			throw new InvalidRecruitmentStatusException();
		}
	}

	private void validateSameCouncil(CouncilManager councilManager, Application application) {
		Long managerCouncilId = councilManager.getCouncil().getId();
		Long applicationCouncilId = application.getRecruitment().getCouncil().getId();

		if (!Objects.equals(managerCouncilId, applicationCouncilId)) {
			throw new CommentForbiddenException();
		}
	}
}
