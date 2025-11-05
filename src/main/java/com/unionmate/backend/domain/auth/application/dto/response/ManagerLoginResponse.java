package com.unionmate.backend.domain.auth.application.dto.response;

public record ManagerLoginResponse(
	String accessToken,
	String refreshToken,
	Long councilId
) {

	public static ManagerLoginResponse of(String accessToken, String refreshToken, Long councilId) {
		return new ManagerLoginResponse(accessToken, refreshToken, councilId);
	}
}
