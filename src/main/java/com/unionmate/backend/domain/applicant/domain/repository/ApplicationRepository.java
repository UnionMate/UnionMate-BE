package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow;
import com.unionmate.backend.domain.council.domain.entity.Council;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findAllByNameAndEmailOrderByIdDesc(String name, String email);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and (
		        a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		        or (
		            a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		            and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		        )
		      )
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListNoFilter(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListSubmitted(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListPassed(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.FAILED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListFailed(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus in (
		      com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW,
		      com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  )
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListNoFilter(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListSubmitted(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.PASSED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListPassed(@Param("council") Council council);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus
		)
		from Application a
		    join a.recruitment r
		where r.council = :council
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.FAILED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListFailed(@Param("council") Council council);
}
