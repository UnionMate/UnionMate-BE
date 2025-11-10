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
public class MailSendEvent {

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;
}
