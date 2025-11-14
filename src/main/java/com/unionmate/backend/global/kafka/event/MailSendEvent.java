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
public class MailSendEvent {

  @JsonProperty("name")
  @NotEmpty
  private String name;

  @JsonProperty("email")
  @NotEmpty
  private String email;

  @JsonProperty("recruitmentId")
  @NotNull
  private Long recruitmentId;
}
