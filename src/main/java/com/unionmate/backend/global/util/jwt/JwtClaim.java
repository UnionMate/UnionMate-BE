package com.unionmate.backend.global.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtClaim {

  // AccessToken
  ID("id"),
  EMAIL("email"),
  name("username"),

  ;

  private final String claim;
  }
