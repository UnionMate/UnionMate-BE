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
public class JwtUserIdEvent {

  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("user_id")
  private Long userId;

  @JsonProperty("valid")
  private Boolean valid;

  @JsonProperty("error_message")
  private String errorMessage;
}
