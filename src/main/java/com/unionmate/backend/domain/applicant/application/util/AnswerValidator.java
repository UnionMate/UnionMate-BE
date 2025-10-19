package com.unionmate.backend.domain.applicant.application.util;

import com.unionmate.backend.domain.applicant.application.dto.request.AnswerRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

public interface AnswerValidator<I extends Item, R extends AnswerRequest> {
	void validate(I itemTemplate, R request);
}
