package com.unionmate.backend.domain.recruitment.entity.item;

import com.unionmate.backend.domain.applicant.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.LocalDateAnswerConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import java.time.LocalDate;
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
@DiscriminatorValue(DiscriminationValue.CALENDAR)
public class CalendarItem extends Item {

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Convert(converter = LocalDateAnswerConverter.class)
  @Lob
  private Answer<LocalDate> answer;
}
