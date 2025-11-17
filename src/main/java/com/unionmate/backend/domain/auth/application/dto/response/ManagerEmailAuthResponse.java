package com.unionmate.backend.domain.auth.application.dto.response;

public record ManagerEmailAuthResponse(
    Boolean isAuthorize
) {

  public static ManagerEmailAuthResponse from(Boolean result) {
    return new ManagerEmailAuthResponse(result);
  }
}
