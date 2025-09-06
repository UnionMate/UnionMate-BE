package com.unionmate.backend.domain.recruitment.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecruitmentStatus {
  DOCUMENT_SCREENING,
  INTERVIEW,
  FINAL,
  ;
}
