package com.unionmate.backend.domain.council.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.application.dto.CreateCouncilRequest;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilResponse;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
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

	public void validateCouncilManagerExists(Member member) {
		if (councilManagerGetService.existsByMember(member)) {
			throw new CouncilManagerAlreadyExistsException();
		}
	}
}
