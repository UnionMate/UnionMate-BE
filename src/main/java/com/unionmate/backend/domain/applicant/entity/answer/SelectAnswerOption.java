package com.unionmate.backend.domain.applicant.entity.answer;

import com.unionmate.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "select_answer_options")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SelectAnswerOption extends BaseEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "is_etc", nullable = false)
  @Builder.Default
  private Boolean isEtc = false;

  @Column(name = "etc_title")
  private String etcTitle;

  @Column(name = "orders", nullable = false)
  private Integer order;

  @Column(name = "is_select", nullable = false)
  private Boolean isSelect;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "select_answer_id")
  @Setter
  private SelectAnswer selectAnswer;

  @AssertTrue
  public boolean validateEtc() {
    if (Boolean.TRUE.equals(isEtc)) {
      return this.etcTitle != null && !this.etcTitle.isEmpty();
    }
    return true;
  }
}
