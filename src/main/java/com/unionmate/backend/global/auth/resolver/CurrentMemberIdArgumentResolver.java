package com.unionmate.backend.global.auth.resolver;

import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.global.auth.dto.AuthRequest;
import com.unionmate.backend.global.auth.dto.AuthResponse;
import com.unionmate.backend.exception.common.InvalidJwtException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final ReplyingKafkaTemplate<String, AuthRequest, AuthResponse> replyingKafkaTemplate;

  @Value("${kafka.topics.auth-request}")
  private String authRequestTopic;

  @Value("${kafka.timeout-ms}")
  private long timeoutMs;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentMemberId.class)
        && (Long.class.isAssignableFrom(parameter.getParameterType()) || long.class.isAssignableFrom(parameter.getParameterType()));
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
    try {
      AuthRequest authRequest = AuthRequest.builder()
          .accessToken(token)
          .build();

      ProducerRecord<String, AuthRequest> record =
          new ProducerRecord<>(authRequestTopic, authRequest);

      RequestReplyFuture<String, AuthRequest, AuthResponse> replyFuture =
          replyingKafkaTemplate.sendAndReceive(record, Duration.ofMillis(timeoutMs));

      AuthResponse authResponse = replyFuture.get().value();

      if (authResponse == null) {
        return handleInvalidToken(parameter);
      }

      if (!authResponse.isSuccess()) {
        return handleInvalidToken(parameter);
      }

      return authResponse.getMemberId();

    } catch (Exception e) {
      return handleInvalidToken(parameter);
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
