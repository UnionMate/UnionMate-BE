package com.unionmate.backend.domain.council.application.dto;

import com.unionmate.backend.domain.council.domain.entity.Council;

public record CreateCouncilResponse(
	long councilId,
	String councilName
) {
	public static CreateCouncilResponse from(Council council) {
		return new CreateCouncilResponse(
			council.getId(),
			council.getName()
		);
	}
}
