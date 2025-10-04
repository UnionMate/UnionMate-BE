package com.unionmate.backend.domain.applicant.entity.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public record Interview(
    @Column(name = "time")
    LocalDateTime time,

    @Column(name = "place")
    String place
) {

  public static Interview init() {
    return new Interview(null, null);
  }
}
