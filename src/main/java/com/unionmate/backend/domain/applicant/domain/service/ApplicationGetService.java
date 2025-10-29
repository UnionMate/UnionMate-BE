package com.unionmate.backend.domain.applicant.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.application.exception.ApplicationNotFoundException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.applicant.domain.repository.ApplicationRepository;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantRow;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
	private final ApplicationRepository applicationRepository;

	public List<Application> getMyApplications(String name, String email) {
		return applicationRepository.findAllByNameAndEmailOrderByIdDesc(name, email);
	}

	public Application getApplicationById(Long applicationId) {
		return applicationRepository.findById(applicationId)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public List<CouncilApplicantRow> getDocumentScreeningApplicantsForCouncil(
		Council council, EvaluationStatus evaluationFilterOrNull
	) {
		List<RecruitmentStatus> statuses = List.of(
			RecruitmentStatus.DOCUMENT_SCREENING,
			RecruitmentStatus.INTERVIEW
		);

		if (evaluationFilterOrNull == null) {
			
			return applicationRepository.findApplicantsForCouncilByStatusesNoFilter(
				council,
				statuses,
				EvaluationStatus.FAILED,
				EvaluationStatus.PASSED
			);
		}

		return applicationRepository.findApplicantsForCouncilByStatusesWithFilter(
			council,
			statuses,
			evaluationFilterOrNull
		);
	}

	public List<CouncilApplicantRow> getInterviewApplicantsForCouncil(
		Council council, EvaluationStatus evaluationFilterOrNull
	) {
		List<RecruitmentStatus> statuses = List.of(
			RecruitmentStatus.INTERVIEW,
			RecruitmentStatus.FINAL
		);

		if (evaluationFilterOrNull == null) {

			return applicationRepository.findApplicantsForCouncilByStatusesNoFilter(
				council,
				statuses,
				EvaluationStatus.FAILED,
				EvaluationStatus.PASSED
			);
		}

		return applicationRepository.findApplicantsForCouncilByStatusesWithFilter(
			council,
			statuses,
			evaluationFilterOrNull
		);
	}
}
