package com.unionmate.backend.domain.council.application.dto;

import com.unionmate.backend.domain.council.domain.entity.Council;

public record UpdateCouncilNameResponse(
	long councilId,
	String councilName
) {
	public static UpdateCouncilNameResponse from(Council council) {
		return new UpdateCouncilNameResponse(
			council.getId(),
			council.getName()
		);
	}
}
