package com.unionmate.backend.domain.applicant.domain.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.exception.CommentForbiddenException;
import com.unionmate.backend.domain.applicant.application.exception.InvalidRecruitmentStatusException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewEvaluationUpdateService {

	private final InterviewEvaluationGetService interviewEvaluationGetService;

	@Transactional
	public void update(Application application, CouncilManager councilManager, Long evaluationId, String newEvaluation) {

		if (application.getRecruitment().getRecruitmentStatus() != RecruitmentStatus.INTERVIEW) {
			throw new InvalidRecruitmentStatusException();
		}

		Long managerCouncilId = councilManager.getCouncil().getId();
		Long appCouncilId = application.getRecruitment().getCouncil().getId();

		if (!Objects.equals(managerCouncilId, appCouncilId)) {
			throw new CommentForbiddenException();
		}

		InterviewEvaluation evaluation = interviewEvaluationGetService.getByIdAndApplication(evaluationId, application);

		if (!Objects.equals(evaluation.getCouncilManager().getCouncil().getId(), managerCouncilId)) {
			throw new CommentForbiddenException();
		}

		evaluation.updateEvaluation(newEvaluation);
	}
}
