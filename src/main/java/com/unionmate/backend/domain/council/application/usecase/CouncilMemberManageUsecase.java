package com.unionmate.backend.domain.council.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
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

	@Transactional
	public void delegateVice(long userId, long newPresidentId) {
		Member currentVice = memberGetService.getMemberById(userId);
		CouncilManager currentManager = councilManagerGetService.getCouncilManagerByMember(currentVice);

		CouncilManager newManager = councilManagerGetService.getCouncilManager(newPresidentId);

		currentManager.validateIsVice();
		currentManager.validateSameCouncil(newManager);

		currentManager.delegateToMember();
		newManager.promoteToVice();
	}
}
