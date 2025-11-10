package com.unionmate.backend.global.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtVerifyEvent {

  @JsonProperty("event_id")
  @NotEmpty
  private String eventId;

  @JsonProperty("access_token")
  @NotEmpty
  private String accessToken;
}
