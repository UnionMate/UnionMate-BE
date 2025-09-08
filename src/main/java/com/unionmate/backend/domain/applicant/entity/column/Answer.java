package com.unionmate.backend.domain.applicant.entity.column;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record Answer<T>(
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY)
    T answer
) {

}
