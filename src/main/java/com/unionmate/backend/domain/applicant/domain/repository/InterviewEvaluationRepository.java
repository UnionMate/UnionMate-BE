package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.InterviewEvaluation;

public interface InterviewEvaluationRepository extends JpaRepository<InterviewEvaluation, Long> {

	@EntityGraph(attributePaths = {"councilManager", "councilManager.member"})
	List<InterviewEvaluation> findAllByApplicationOrderByCreatedAtDesc(Application application);

	Optional<InterviewEvaluation> findByIdAndApplication(Long id, Application application);
}
