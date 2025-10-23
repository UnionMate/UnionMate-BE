package com.unionmate.backend.domain.auth.presentation;

import static com.unionmate.backend.domain.auth.presentation.AuthResponseCode.*;

import com.unionmate.backend.domain.auth.application.dto.request.ManagerLoginRequest;
import com.unionmate.backend.domain.auth.application.dto.request.ManagerRegisterRequest;
import com.unionmate.backend.domain.auth.application.dto.response.ManagerLoginResponse;
import com.unionmate.backend.domain.auth.application.dto.response.ManagerRegisterResponse;
import com.unionmate.backend.domain.auth.application.dto.response.ReissueResponse;
import com.unionmate.backend.domain.auth.application.usecase.AuthUseCase;
import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.global.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthUseCase authUseCase;

  @PostMapping("/manager/register")
  public CommonResponse<ManagerRegisterResponse> postManagerRegister(
      @RequestBody @Valid ManagerRegisterRequest managerRegisterRequest
  ) {
    return CommonResponse.success(
        MANAGER_REGISTER_SUCCESS,
        this.authUseCase.managerRegister(managerRegisterRequest)
    );
  }

  @PostMapping("/manager/login")
  public CommonResponse<ManagerLoginResponse> postManagerLogin(
      @RequestBody @Valid ManagerLoginRequest managerLoginRequest
  ) {
    return CommonResponse.success(
        MANAGER_LOGIN_SUCCESS,
        this.authUseCase.managerLogin(managerLoginRequest)
    );
  }

  @PostMapping("/reissue")
  public CommonResponse<ReissueResponse> postReissue(
      @CurrentMemberId Long memberId
  ) {
    return CommonResponse.success(
        REISSUE_SUCCESS,
        this.authUseCase.reissue(memberId)
    );
  }
}
