package com.unionmate.backend.domain.applicant.entity;

import com.unionmate.backend.domain.council.entity.CouncilManager;
import com.unionmate.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "interview_evaluations")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewEvaluation extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private CouncilManager councilManager;

  @Column(name = "evaluation", nullable = false)
  private String evaluation;
}
