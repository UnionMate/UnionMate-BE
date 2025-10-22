package com.unionmate.backend.domain.council.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.application.dto.CouncilMemberResponse;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilGetService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerDeleteService;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.service.MemberGetService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouncilMemberManageUsecase {
	private final MemberGetService memberGetService;
	private final CouncilManagerGetService councilManagerGetService;
	private final CouncilManagerDeleteService councilManagerDeleteService;
	private final CouncilGetService councilGetService;

	@Transactional
	public void delegateVice(long userId, long newPresidentId) {
		Member currentVice = memberGetService.getMemberById(userId);
		CouncilManager currentManager = councilManagerGetService.getCouncilManagerByMember(currentVice);

		CouncilManager newManager = councilManagerGetService.getCouncilManager(newPresidentId);

		currentManager.isVice();
		currentManager.validateSameCouncil(newManager);

		currentManager.delegateToMember();
		newManager.promoteToVice();
	}

	public List<CouncilMemberResponse> getAllCouncilMembers(long councilId) {
		Council council = councilGetService.getCouncilById(councilId);

		return councilManagerGetService.getAllCouncilMembers(council)
			.stream()
			.map(CouncilMemberResponse::from)
			.toList();
	}

	@Transactional
	public void removeCouncilMember(long memberId, long councilManagerId) {
		CouncilManager requester = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		CouncilManager targetManager = councilManagerGetService.getCouncilManager(councilManagerId);

		requester.validateSameCouncil(targetManager);

		councilManagerDeleteService.delete(targetManager);
	}
}
