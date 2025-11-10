package com.unionmate.backend.domain.recruitment.application.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IllegalRecruitmentStatus extends ApplicationException {

  public IllegalRecruitmentStatus() {
    super(ErrorCode.ILLEGAL_RECRUITMENT_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
