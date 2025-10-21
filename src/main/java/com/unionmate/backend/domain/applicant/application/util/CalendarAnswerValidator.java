package com.unionmate.backend.domain.applicant.application.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.application.dto.request.CalendarAnswerRequest;
import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;

@Component
public class CalendarAnswerValidator implements AnswerValidator<CalendarItem, CalendarAnswerRequest> {
	@Override
	public void validate(CalendarItem calendarItem, CalendarAnswerRequest calendarAnswerRequest) {
		LocalDate date = calendarAnswerRequest.date();

		if (Boolean.TRUE.equals(calendarItem.getRequired()) && date == null) {
			throw new RequiredAnswerMissingException();
		}
	}
}
