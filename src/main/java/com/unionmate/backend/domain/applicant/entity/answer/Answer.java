package com.unionmate.backend.domain.applicant.entity.answer;

import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.applicant.entity.Application;
import com.unionmate.backend.domain.applicant.entity.enums.AnswerType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "answers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answer_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Answer extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "answer_type", insertable = false, updatable = false)
  private AnswerType answerType;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "orders", nullable = false)
  private Integer order;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;
}
