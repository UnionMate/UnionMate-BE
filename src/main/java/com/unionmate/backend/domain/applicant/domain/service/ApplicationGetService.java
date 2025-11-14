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

	public List<CouncilApplicantQueryRow> getDocumentScreeningApplicantsForRecruitment(
		Recruitment recruitment, EvaluationStatus evaluationFilterOrNull
	) {
		if (evaluationFilterOrNull == null) {

			return applicationRepository.findDocumentListNoFilter(recruitment);
		}
		return switch (evaluationFilterOrNull) {
			case SUBMITTED -> applicationRepository.findDocumentListSubmitted(recruitment);
			case PASSED -> applicationRepository.findDocumentListPassed(recruitment);
			case FAILED -> applicationRepository.findDocumentListFailed(recruitment);
			default -> applicationRepository.findDocumentListNoFilter(recruitment);
		};
	}

	public List<CouncilApplicantQueryRow> getInterviewApplicantsForCouncil(
		Recruitment recruitment, EvaluationStatus evaluationFilterOrNull
	) {
		if (evaluationFilterOrNull == null) {

			return applicationRepository.findInterviewListNoFilter(recruitment);
		}
		return switch (evaluationFilterOrNull) {
			case SUBMITTED -> applicationRepository.findInterviewListSubmitted(recruitment);
			case PASSED -> applicationRepository.findInterviewListPassed(recruitment);
			case FAILED -> applicationRepository.findInterviewListFailed(recruitment);
			default -> applicationRepository.findInterviewListNoFilter(recruitment);
		};
	}

	public boolean existsByRecruitmentId(Long recruitmentId) {
		return applicationRepository.existsByRecruitmentId(recruitmentId);
	}

	public List<Application> getApplicationsByRecruitment(Recruitment recruitment) {
		return this.applicationRepository.findByRecruitment(recruitment);
	}
}
