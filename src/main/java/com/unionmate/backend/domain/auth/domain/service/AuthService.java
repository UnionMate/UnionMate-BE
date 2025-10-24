package com.unionmate.backend.domain.auth.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public String encodePassword(String rawPassword) {
    return this.bCryptPasswordEncoder.encode(rawPassword);
  }

  public boolean isValidPassword(String rawPassword, String encodedPassword) {
    return this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }
}
