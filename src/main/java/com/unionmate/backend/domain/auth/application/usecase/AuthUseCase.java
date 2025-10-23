package com.unionmate.backend.domain.auth.application.usecase;

import com.unionmate.backend.domain.auth.application.dto.request.ManagerRegisterRequest;
import com.unionmate.backend.domain.auth.application.dto.response.ManagerRegisterResponse;
import com.unionmate.backend.domain.auth.domain.service.AuthService;
import com.unionmate.backend.domain.auth.exception.EmailDuplicateException;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.service.MemberGetService;
import com.unionmate.backend.domain.member.domain.service.MemberSaveService;
import com.unionmate.backend.global.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

  private final AuthService authService;
  private final MemberGetService memberGetService;
  private final MemberSaveService memberSaveService;
  private final JwtProvider jwtProvider;

  @Transactional
  public ManagerRegisterResponse managerRegister(ManagerRegisterRequest managerRegisterRequest) {
    if (this.memberGetService.existsByEmail(managerRegisterRequest.email())) {
      throw new EmailDuplicateException();
    }

    // TODO: 학교 이메일 인증

    String encodePassword = this.authService.encodePassword(managerRegisterRequest.password());

    Member member = Member.builder()
        .name(managerRegisterRequest.name())
        .email(managerRegisterRequest.email())
        .password(encodePassword)
        .build();

    Member persisted = this.memberSaveService.save(member);

    String accessToken = this.jwtProvider.generateAccessToken(persisted);
    String refreshToken = this.jwtProvider.generateRefreshToken(persisted.getId());

    return ManagerRegisterResponse.of(accessToken, refreshToken);
  }
}
