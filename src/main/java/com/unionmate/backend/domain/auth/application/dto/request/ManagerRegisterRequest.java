package com.unionmate.backend.domain.auth.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ManagerRegisterRequest(
    @NotBlank(message = "이름은 필수입니다")
    String name,

    @Email(message = "이메일 형식이어야합니다")
    @NotBlank(message = "이메일은 필수입니다")
    String email,

    @NotBlank(message = "비밀번호는 필수입니다")
    String password
) {

}
