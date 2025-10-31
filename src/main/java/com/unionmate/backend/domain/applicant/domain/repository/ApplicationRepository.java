package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findAllByNameAndEmailOrderByIdDesc(String name, String email);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		  join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus in :statuses
		order by
		  case
		    when a.stage.evaluationStatus = :failed then 0
		    when a.stage.evaluationStatus = :passed then 1
		    else 2
		  end,
		  a.id desc
		""")
	List<CouncilApplicantQueryRow> findApplicantsForCouncilByStatusesNoFilter(
		@Param("council") Council council,
		@Param("statuses") List<RecruitmentStatus> statuses,
		@Param("failed") EvaluationStatus failed,
		@Param("passed") EvaluationStatus passed
	);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		  join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus in :statuses
		  and a.stage.evaluationStatus = :evaluationStatus
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findApplicantsForCouncilByStatusesWithFilter(
		@Param("council") Council council,
		@Param("statuses") List<RecruitmentStatus> statuses,
		@Param("evaluationStatus") EvaluationStatus evaluationStatus
	);
}
