package com.unionmate.backend.global.auth.resolver;

import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.exception.common.InvalidJwtException;
import com.unionmate.backend.global.util.jwt.JwtAuthenticator;
import com.unionmate.backend.global.util.jwt.JwtExtractor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtAuthenticator jwtAuthenticator;
  private final JwtExtractor jwtExtractor;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentMemberId.class)
        && Long.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory
  ) {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    if (request == null) {
      return handleMissingToken(parameter);
    }

    String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
    if (!StringUtils.hasText(authorizationHeader)) {
      return handleMissingToken(parameter);
    }

    if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
      return handleInvalidToken(parameter);
    }

    String token = extractToken(authorizationHeader);
    if (!StringUtils.hasText(token)) {
      return handleInvalidToken(parameter);
    }

    return extractMemberId(token, parameter);
  }

  private String extractToken(String authorizationHeader) {
    return authorizationHeader.substring(BEARER_PREFIX.length());
  }

  private Long extractMemberId(String token, MethodParameter parameter) {
    this.jwtAuthenticator.verifyAccessToken(token);

    Claims claims = this.jwtExtractor.parseAccessTokenPayloads(token);

    String subject = claims.getSubject();
    if (!StringUtils.hasText(subject)) {
      return handleInvalidToken(parameter);
    }

    try {
      return Long.parseLong(subject);
    } catch (NumberFormatException e) {
      throw new InvalidJwtException();
    }
  }

  private Long handleMissingToken(MethodParameter parameter) {
    CurrentMemberId annotation = parameter.getParameterAnnotation(CurrentMemberId.class);
    if (annotation != null && !annotation.required()) {
      return null;
    }
    throw new InvalidJwtException();
  }

  private Long handleInvalidToken(MethodParameter parameter) {
    CurrentMemberId annotation = parameter.getParameterAnnotation(CurrentMemberId.class);
    if (annotation != null && !annotation.required()) {
      return null;
    }
    throw new InvalidJwtException();
  }
}
