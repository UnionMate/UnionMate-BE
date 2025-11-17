package com.unionmate.backend.domain.auth.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ManagerEmailAuthRequest(
    @Email
    @NotNull
    String email,

    @NotNull
    String univName
) {
}
