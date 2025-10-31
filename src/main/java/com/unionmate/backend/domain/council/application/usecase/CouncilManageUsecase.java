package com.unionmate.backend.domain.council.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.domain.entity.enums.EvaluationStatus;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantQueryRow;
import com.unionmate.backend.domain.council.application.dto.CouncilApplicantResponse;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilRequest;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilResponse;
import com.unionmate.backend.domain.council.application.dto.UpdateCouncilNameRequest;
import com.unionmate.backend.domain.council.application.dto.UpdateCouncilNameResponse;
import com.unionmate.backend.domain.council.application.dto.UpdateInvitationCodeRequest;
import com.unionmate.backend.domain.council.application.dto.UpdateInvitationCodeResponse;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilGetService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerSaveService;
import com.unionmate.backend.domain.council.domain.service.CouncilSaveService;
import com.unionmate.backend.domain.council.exception.CouncilManagerAlreadyExistsException;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.entity.School;
import com.unionmate.backend.domain.member.domain.service.MemberGetService;
import com.unionmate.backend.domain.member.domain.service.SchoolGetService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouncilManageUsecase {
	private final MemberGetService memberGetService;
	private final SchoolGetService schoolGetService;
	private final ApplicationGetService applicationGetService;
	private final CouncilManagerGetService councilManagerGetService;
	private final CouncilGetService councilGetService;

	private final CouncilSaveService councilSaveService;
	private final CouncilManagerSaveService councilManagerSaveService;

	@Transactional
	public CreateCouncilResponse createCouncil(long memberId, CreateCouncilRequest dto) {
		Member member = memberGetService.getMemberById(memberId);
		validateCouncilManagerExists(member);

		School school = schoolGetService.getSchoolByEmailDomain(member.getEmail());

		// Council 생성
		Council council = councilSaveService.createCouncil(Council.createCouncil(dto.name()));

		// CouncilManager 생성 (회장)
		CouncilManager vice = CouncilManager.createVice(member, school, council);
		councilManagerSaveService.save(vice);

		return CreateCouncilResponse.from(council);
	}

	@Transactional
	public CreateCouncilResponse signUpCouncilManager(long memberId, String invitationCode) {
		Member member = memberGetService.getMemberById(memberId);
		validateCouncilManagerExists(member);
		Council council = councilGetService.getCouncilById(invitationCode);

		School school = schoolGetService.getSchoolByEmailDomain(member.getEmail());

		CouncilManager manager = CouncilManager.createMember(member, school, council);
		councilManagerSaveService.save(manager);

		return CreateCouncilResponse.from(council);
	}

	@Transactional
	public UpdateCouncilNameResponse updateCouncilName(long memberId, long councilId,
		UpdateCouncilNameRequest request) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Council council = councilGetService.getCouncilById(councilId);
		councilManager.isViceOfCouncil(council);

		council.updateName(request.name());

		return UpdateCouncilNameResponse.from(council);
	}

	@Transactional
	public UpdateInvitationCodeResponse updateInvitationCode(long memberId, long councilId,
		UpdateInvitationCodeRequest request) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Council council = councilGetService.getCouncilById(councilId);
		councilManager.isViceOfCouncil(council);

		council.updateInvitationCode(request.invitationCode());

		return UpdateInvitationCodeResponse.from(council);
	}

	private void validateCouncilManagerExists(Member member) {
		if (councilManagerGetService.existsByMember(member)) {
			throw new CouncilManagerAlreadyExistsException();
		}
	}

	public List<CouncilApplicantResponse> getDocumentScreeningApplicants(
		long memberId, long councilId, EvaluationStatus evaluationFilterOrNull
	) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Council council = councilGetService.getCouncilById(councilId);
		councilManager.validateBelongsToCouncil(councilManager, council);

		List<CouncilApplicantQueryRow> rows = applicationGetService.getDocumentScreeningApplicantsForCouncil(council,
			evaluationFilterOrNull);

		return rows.stream()
			.map(row -> CouncilApplicantResponse.of(
				row.name(), row.email(), row.tel(), row.appliedAt(), row.evaluationStatus()
			))
			.toList();
	}

	public List<CouncilApplicantResponse> getInterviewApplicants(
		long memberId, long councilId, EvaluationStatus evaluationFilterOrNull
	) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Council council = councilGetService.getCouncilById(councilId);
		councilManager.validateBelongsToCouncil(councilManager, council);

		List<CouncilApplicantQueryRow> rows = applicationGetService.getInterviewApplicantsForCouncil(council,
			evaluationFilterOrNull);

		return rows.stream()
			.map(row -> CouncilApplicantResponse.of(
				row.name(), row.email(), row.tel(), row.appliedAt(), row.evaluationStatus()
			))
			.toList();
	}
}
