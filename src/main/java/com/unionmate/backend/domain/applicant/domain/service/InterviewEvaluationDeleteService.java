package com.unionmate.backend.domain.applicant.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;
import com.unionmate.backend.domain.applicant.domain.repository.InterviewEvaluationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewEvaluationDeleteService {

	private final InterviewEvaluationRepository interviewEvaluationRepository;

	@Transactional
	public void delete(InterviewEvaluation interviewEvaluation) {
		interviewEvaluationRepository.delete(interviewEvaluation);
	}
}
