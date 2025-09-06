package com.unionmate.backend.domain.applicant.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AnswerType {
  SELECT(DiscriminationValue.SELECT),
  TEXT(DiscriminationValue.TEXT),
  CALENDAR(DiscriminationValue.CALENDAR),
  ANNOUNCEMENT(DiscriminationValue.ANNOUNCEMENT),
  ;

  private final String discriminationValue;

  public static abstract class DiscriminationValue {
    public static final String SELECT = "SELECT";
    public static final String TEXT = "TEXT";
    public static final String CALENDAR = "CALENDAR";
    public static final String ANNOUNCEMENT = "ANNOUNCEMENT";
  }
}
