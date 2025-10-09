package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType.DiscriminationValue;
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

  //Single table 전략인데 하위 타입 전용 컬럼인 text를 nullable = false로 해서는 안됩니다.
  //items 테이블에 모든 하위 컬럼이 있고, SELECT 타입을 insert할 때 값이 없는데도 not null을 요구한다면 오류가 생깁니다.
  @Column(name = "text", length = 500)
  private String text;

  @Column(name = "text_max_length")
  private Integer maxLength;

  @Convert(converter = StringAnswerConverter.class)
  @Lob
  private Answer<String> answer;
}
