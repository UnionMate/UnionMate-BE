package com.unionmate.backend.global.util.jwt;

import com.unionmate.backend.domain.member.domain.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final String accessValidity;
  private final String refreshValidity;

  private final SecretKey accessKey;
  private final SecretKey refreshKey;

  public JwtProvider(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.access.validity}") String accessValidity,
      @Value("${jwt.refresh.secret}") String refreshSecret,
      @Value("${jwt.refresh.validity}") String refreshValidity
  ) {
    this.refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshSecret.getBytes()));
    this.accessKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecret.getBytes()));

    this.accessValidity = accessValidity;
    this.refreshValidity = refreshValidity;
  }

  public String generateAccessToken(Member member) {
    return generateAccessToken(
        member.getId(),
        member.getEmail(),
        member.getName()
    );
  }

  public String generateAccessToken(
      Long userId,
      String email,
      String name
  ) {
    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim(JwtClaim.name.getClaim(), name)
        .claim(JwtClaim.EMAIL.getClaim(), email)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + Long.parseLong(accessValidity)))
        .signWith(this.accessKey, Jwts.SIG.HS256)
        .compact();
  }

  public String generateRefreshToken(Long memberId) {
    return Jwts.builder()
        .subject(String.valueOf(memberId))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(
            System.currentTimeMillis() + Long.parseLong(refreshValidity)
        ))
        .signWith(refreshKey, Jwts.SIG.HS256)
        .compact();
  }
}
