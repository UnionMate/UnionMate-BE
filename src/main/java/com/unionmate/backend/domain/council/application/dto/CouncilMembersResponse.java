package com.unionmate.backend.domain.council.application.dto;

import java.util.List;

public record CouncilMembersResponse(
	long councilId,
	String councilName,
	List<CouncilMemberResponse> members
) {
}
