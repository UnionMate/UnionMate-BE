package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantRow;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findAllByNameAndEmailOrderByIdDesc(String name, String email);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		  join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = :recruitmentStatus
		order by
		  case
		    when a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.FAILED then 0
		    when a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.PASSED then 1
		    else 2
		  end asc,
		  a.id desc
		""")
	List<CouncilApplicantRow> findApplicantsForCouncilNoFilter(Council council, RecruitmentStatus recruitmentStatus);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		  join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = :recruitmentStatus
		  and a.stage.evaluationStatus = :evaluationStatus
		order by a.id desc
		""")
	List<CouncilApplicantRow> findApplicantsForCouncilWithFilter(Council council, RecruitmentStatus recruitmentStatus,
		EvaluationStatus evaluationStatus);
}
