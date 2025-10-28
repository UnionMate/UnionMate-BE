package com.unionmate.backend.global.util.jwt;

import com.unionmate.backend.exception.common.ExpireJwtException;
import com.unionmate.backend.exception.common.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticator {

  private final SecretKey accessKey;
  private final SecretKey refreshKey;

  public JwtAuthenticator(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.refresh.secret}") String refreshSecret
  ) {
    this.refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshSecret.getBytes()));
    this.accessKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecret.getBytes()));
  }

  public void verifyAccessToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(accessKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (SignatureException | DeserializationException | MalformedJwtException e) {
      throw new InvalidJwtException();
    } catch (ExpiredJwtException e) {
      throw new ExpireJwtException();
    }
  }

  public void verifyRefreshToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(refreshKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();

    } catch (SignatureException | DeserializationException | MalformedJwtException e) {
      throw new InvalidJwtException();
    } catch (ExpiredJwtException e) {
      throw new ExpireJwtException();
    }
  }
}
