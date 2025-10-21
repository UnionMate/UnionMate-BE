package com.unionmate.backend.domain.council.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.application.dto.CreateCouncilRequest;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilResponse;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilGetService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.council.domain.service.CouncilSaveService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerSaveService;
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
	private final CouncilSaveService councilSaveService;
	private final CouncilManagerSaveService councilManagerSaveService;
	private final CouncilManagerGetService councilManagerGetService;
	private final CouncilGetService councilGetService;

	@Transactional
	public CreateCouncilResponse createCouncil(long memberId, CreateCouncilRequest dto) {
		Member member = memberGetService.getMemberById(memberId);
		validateCouncilManagerExists(member);

		School school = schoolGetService.getSchoolByEmailDomain(member.getEmail());

		// Council 생성
		Council council = councilSaveService.createCouncil(Council.createCouncil(dto.name()));

		// CouncilManager 생성 (회장)
		CouncilManager vice = CouncilManager.LinkToVice(member, school, council);
		councilManagerSaveService.save(vice);

		return new CreateCouncilResponse(council.getId(), council.getName());
	}

	@Transactional
	public CreateCouncilResponse signUpCouncilManager(long memberId, String invitationCode) {
		Member member = memberGetService.getMemberById(memberId);
		validateCouncilManagerExists(member);
		Council council = councilGetService.getCouncilById(invitationCode);

		School school = schoolGetService.getSchoolByEmailDomain(member.getEmail());

		CouncilManager manager = CouncilManager.LinkToMember(member, school, council);
		councilManagerSaveService.save(manager);

		return new CreateCouncilResponse(council.getId(), council.getName());
	}

	public void validateCouncilManagerExists(Member member) {
		if (councilManagerGetService.existsByMember(member)) {
			throw new CouncilManagerAlreadyExistsException();
		}
	}
}
