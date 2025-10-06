package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.LongArrayAnswerConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class SelectItem extends Item {

  @OneToMany(mappedBy = "selectItem", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<SelectItemOption> selectItemOptions = new ArrayList<>();

  @Convert(converter = LongArrayAnswerConverter.class)
  @Lob
  // 선택된 SelectItemOption의 PK 리스트
  private Answer<List<Long>> answer;
}
