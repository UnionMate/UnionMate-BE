package com.unionmate.backend.domain.applicant.entity.answer;

import com.unionmate.backend.domain.applicant.entity.enums.AnswerType.DiscriminationValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue(DiscriminationValue.SELECT)
public class SelectAnswer extends Answer {

  @OneToMany(mappedBy = "selectAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<SelectAnswerOption> selectAnswerOptions = new ArrayList<>();
}
