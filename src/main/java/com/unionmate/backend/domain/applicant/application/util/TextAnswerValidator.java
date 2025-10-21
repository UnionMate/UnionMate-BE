package com.unionmate.backend.domain.applicant.application.util;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.application.dto.request.TextAnswerRequest;
import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
import com.unionmate.backend.domain.applicant.application.exception.TextTooLongException;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

@Component
public class TextAnswerValidator implements AnswerValidator<TextItem, TextAnswerRequest> {
	@Override
	public void validate(TextItem textItem, TextAnswerRequest textAnswerRequest) {
		String text = textAnswerRequest.text();

		if (Boolean.TRUE.equals(textItem.getRequired()) && (text == null || text.isBlank())) {
			throw new RequiredAnswerMissingException();
		}
		if (textItem.getMaxLength() != null && text != null
			&& text.length() > textItem.getMaxLength()) {
			throw new TextTooLongException();
		}
	}
}
