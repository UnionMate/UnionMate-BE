package com.unionmate.backend.domain.auth.application.dto.response;

public record ManagerRegisterResponse(
    String accessToken,
    String refreshToken
) {

  public static ManagerRegisterResponse of(String accessToken, String refreshToken) {
    return new ManagerRegisterResponse(accessToken, refreshToken);
  }
}
