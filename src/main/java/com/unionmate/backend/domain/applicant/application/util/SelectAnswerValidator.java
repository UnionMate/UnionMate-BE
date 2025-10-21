package com.unionmate.backend.domain.applicant.application.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.application.dto.request.SelectAnswerRequest;
import com.unionmate.backend.domain.applicant.application.exception.OptionInvalidException;
import com.unionmate.backend.domain.applicant.application.exception.PluralSelectException;
import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;

@Component
public class SelectAnswerValidator implements AnswerValidator<SelectItem, SelectAnswerRequest> {
	@Override
	public void validate(SelectItem selectItem, SelectAnswerRequest selectAnswerRequest) {
		List<Long> selection = Optional.ofNullable(selectAnswerRequest.optionIds()).orElse(List.of());

		if (Boolean.TRUE.equals(selectItem.getRequired()) && selection.isEmpty()) {
			throw new RequiredAnswerMissingException();
		}
		if (!selectItem.isMultiple() && selection.size() > 1) {
			throw new PluralSelectException();
		}

		Set<Long> validOptions = selectItem.getSelectItemOptions().stream()
			.map(SelectItemOption::getId).collect(Collectors.toSet());
		if (!validOptions.containsAll(selection)) {
			throw new OptionInvalidException();
		}
	}
}
