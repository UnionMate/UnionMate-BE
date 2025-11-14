package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByRecruitment(Recruitment recruitment);

	List<Application> findAllByNameAndEmailOrderByIdDesc(String name, String email);

	boolean existsByRecruitmentId(Long recruitmentId);

	Optional<Application> findByIdAndNameAndEmail(Long id, String name, String email);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and (
		        a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		        or (
		            a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		            and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		        )
		      )
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListNoFilter(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListSubmitted(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListPassed(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.DOCUMENT_SCREENING
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.FAILED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findDocumentListFailed(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus in (
		      com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW,
		      com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  )
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListNoFilter(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.INTERVIEW
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.SUBMITTED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListSubmitted(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.PASSED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListPassed(@Param("recruitment") Recruitment recruitment);

	@Query("""
		select new com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow(
		    a.name, a.email, a.tel, a.createdAt, a.stage.evaluationStatus, a.stage.recruitmentStatus
		)
		from Application a
		    join a.recruitment r
		where r = :recruitment
		  and a.stage.recruitmentStatus = com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus.FINAL
		  and a.stage.evaluationStatus = com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus.FAILED
		order by a.id desc
		""")
	List<CouncilApplicantQueryRow> findInterviewListFailed(@Param("recruitment") Recruitment recruitment);

	@EntityGraph(attributePaths = {"recruitment", "recruitment.council", "answers"})
	@Query("select a from Application a where a.id = :id")
	Optional<Application> findByIdWithRecruitmentAndAnswers(@Param("id") Long id);

	@EntityGraph(attributePaths = {"recruitment", "recruitment.council"})
	Optional<Application> findByRecruitmentIdAndNameIgnoreCaseAndEmailIgnoreCase(
		Long recruitmentId,
		String name,
		String email
	);
}
