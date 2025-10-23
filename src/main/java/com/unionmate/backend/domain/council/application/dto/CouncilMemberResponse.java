package com.unionmate.backend.domain.council.application.dto;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.entity.enums.CouncilRole;

public record CouncilMemberResponse(
	long councilManagerId,
	String memberName,
	String memberEmail,
	CouncilRole councilRole
) {
	public static CouncilMemberResponse from(CouncilManager councilManager) {
		return new CouncilMemberResponse(
			councilManager.getId(),
			councilManager.getMember().getName(),
			councilManager.getMember().getEmail(),
			councilManager.getCouncilRole()
		);
	}
}
