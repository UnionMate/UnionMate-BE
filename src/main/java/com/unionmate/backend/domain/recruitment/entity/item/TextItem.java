package com.unionmate.backend.domain.recruitment.entity.item;

import com.unionmate.backend.domain.applicant.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.StringAnswerConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class TextItem extends Item{

  @Column(name = "text", nullable = false, length = 500)
  private String text;

  @Convert(converter = StringAnswerConverter.class)
  @Lob
  private Answer<String> answer;
}
