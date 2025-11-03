package com.unionmate.backend.domain.applicant.application.usecase;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.SetInterviewScheduleRequest;
import com.unionmate.backend.domain.applicant.application.exception.ApplicationEvaluationForbiddenException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.embed.Interview;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationSaveService;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewScheduleUseCase {

	private final ApplicationGetService applicationGetService;
	private final ApplicationSaveService applicationSaveService;
	private final CouncilManagerGetService councilManagerGetService;

	@Transactional
	public void setInterviewSchedule(Long memberId, Long applicationId, SetInterviewScheduleRequest request) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		validateSameCouncil(councilManager, application);

		Interview newInterview = new Interview(request.time(), request.place());
		application.updateInterview(newInterview);
		applicationSaveService.save(application);
	}

	private void validateSameCouncil(CouncilManager councilManager, Application application) {
		Long managerCouncilId = councilManager.getCouncil().getId();
		Long applicationCouncilId = application.getRecruitment().getCouncil().getId();
		if (!Objects.equals(managerCouncilId, applicationCouncilId)) {
			throw new ApplicationEvaluationForbiddenException();
		}
	}
}