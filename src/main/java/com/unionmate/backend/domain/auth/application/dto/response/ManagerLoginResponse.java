package com.unionmate.backend.domain.auth.application.dto.response;

public record ManagerLoginResponse(
    String accessToken,
    String refreshToken
) {

  public static ManagerLoginResponse of(String accessToken, String refreshToken) {
    return new ManagerLoginResponse(accessToken, refreshToken);
  }
}
