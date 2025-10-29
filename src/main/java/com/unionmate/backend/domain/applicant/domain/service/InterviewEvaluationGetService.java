package com.unionmate.backend.domain.applicant.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.application.exception.InterviewEvaluationNotFoundException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;
import com.unionmate.backend.domain.applicant.domain.repository.InterviewEvaluationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewEvaluationGetService {

	private final InterviewEvaluationRepository interviewEvaluationRepository;

	public InterviewEvaluation getByIdAndApplication(Long id, Application application) {
		return interviewEvaluationRepository.findByIdAndApplication(id, application)
			.orElseThrow(InterviewEvaluationNotFoundException::new);
	}

	public List<InterviewEvaluation> getAllByApplication(Application application) {
		return interviewEvaluationRepository.findAllByApplicationOrderByCreatedAtDesc(application);
	}
}
