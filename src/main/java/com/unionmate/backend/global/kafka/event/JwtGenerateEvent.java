package com.unionmate.backend.global.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotEmpty
  private String eventId;

  @JsonProperty("user_id")
  @NotNull
  private Long userId;

  @JsonProperty("email")
  @NotEmpty
  private String email;

  @JsonProperty("name")
  @NotEmpty
  private String name;
}
