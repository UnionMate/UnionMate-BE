package com.unionmate.backend.domain.applicant.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.application.exception.ApplicationNotFoundException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.applicant.domain.repository.ApplicationRepository;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {

	private final ApplicationRepository applicationRepository;

	public List<Application> getMyApplications(String name, String email) {
		return applicationRepository.findAllByNameAndEmailOrderByIdDesc(name, email);
	}

	public Application getApplicationWithDetails(Long applicationId) {
		return applicationRepository.findByIdWithRecruitmentAndAnswers(applicationId)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public Application getApplicationById(Long applicationId) {
		return applicationRepository.findById(applicationId)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public Application getMyOneApplication(Long applicationId, String name, String email) {
		return applicationRepository.findByIdAndNameAndEmail(applicationId, name, email)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public Application getByRecruitmentIdAndNameAndEmailWithRecruitmentAndCouncil(
		Long recruitmentId,
		String applicantName,
		String email
	) {
		return applicationRepository
			.findByRecruitmentIdAndNameIgnoreCaseAndEmailIgnoreCase(
				recruitmentId, applicantName, email
			)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public List<CouncilApplicantQueryRow> getDocumentScreeningApplicantsForCouncil(
		Council council, EvaluationStatus evaluationFilterOrNull
	) {
		if (evaluationFilterOrNull == null) {

			return applicationRepository.findDocumentListNoFilter(council);
		}
		return switch (evaluationFilterOrNull) {
			case SUBMITTED -> applicationRepository.findDocumentListSubmitted(council);
			case PASSED -> applicationRepository.findDocumentListPassed(council);
			case FAILED -> applicationRepository.findDocumentListFailed(council);
			default -> applicationRepository.findDocumentListNoFilter(council);
		};
	}

	public List<CouncilApplicantQueryRow> getInterviewApplicantsForCouncil(
		Council council, EvaluationStatus evaluationFilterOrNull
	) {
		if (evaluationFilterOrNull == null) {

			return applicationRepository.findInterviewListNoFilter(council);
		}
		return switch (evaluationFilterOrNull) {
			case SUBMITTED -> applicationRepository.findInterviewListSubmitted(council);
			case PASSED -> applicationRepository.findInterviewListPassed(council);
			case FAILED -> applicationRepository.findInterviewListFailed(council);
			default -> applicationRepository.findInterviewListNoFilter(council);
		};
	}

	public boolean existsByRecruitmentId(Long recruitmentId) {
		return applicationRepository.existsByRecruitmentId(recruitmentId);
	}

	public List<Application> getApplicationsByRecruitment(Recruitment recruitment) {
		return this.applicationRepository.findByRecruitment(recruitment);
	}
}
