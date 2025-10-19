package com.unionmate.backend.domain.applicant.application.exception;

import com.unionmate.backend.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateSelectException extends ApplicationException {
    public DuplicateSelectException() {
        super(ErrorCode.DUPLICATE_SELECT_INVALID, HttpStatus.BAD_REQUEST);
    }
}
