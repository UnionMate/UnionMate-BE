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
public class JwtGenerateEvent {

  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("user_id")
  private Long userId;

  @JsonProperty("email")
  private String email;

  @JsonProperty("name")
  private String name;
}
