package com.unionmate.backend.domain.applicant.entity.answer;

import com.unionmate.backend.domain.applicant.entity.enums.AnswerType.DiscriminationValue;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue(DiscriminationValue.TEXT)
public class TextAnswer extends Answer {

  @Column(name = "text", nullable = false, length = 500)
  private String text;
}
