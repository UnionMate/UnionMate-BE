package com.unionmate.backend.global.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenEvent {

  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("access_token_expires_in")
  private Long accessTokenExpiresIn;

  @JsonProperty("refresh_token_expires_in")
  private Long refreshTokenExpiresIn;
}
