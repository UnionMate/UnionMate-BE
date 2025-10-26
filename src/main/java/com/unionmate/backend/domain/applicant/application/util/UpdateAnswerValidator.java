package com.unionmate.backend.domain.applicant.application.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

@Component
public class UpdateAnswerValidator {
	public void validateRequiredAnswers(Application application, Recruitment recruitment) {
		boolean missing = recruitment.getItems().stream()
			.filter(item -> item.getItemType() != ItemType.ANNOUNCEMENT)
			.filter(item -> Boolean.TRUE.equals(item.getRequired()))
			.anyMatch(item -> !hasNonEmptyAnswer(application, item));

		if (missing) {
			throw new RequiredAnswerMissingException();
		}
	}

	private boolean hasNonEmptyAnswer(Application application, Item item) {
		return application.getAnswers().stream()
			.anyMatch(answer -> {
				if (answer.getItemType() != item.getItemType()) {
					return false;
				}
				if (!Objects.equals(answer.getTitle(), item.getTitle())) {
					return false;
				}
				if (!Objects.equals(answer.getOrder(), item.getOrder())) {
					return false;
				}

				return switch (answer.getItemType()) {
					case TEXT -> {
						TextItem textItem = (TextItem)answer;
						Answer<String> a = textItem.getAnswer();
						yield a != null && Objects.requireNonNull(a).answer() != null && !a.answer().isBlank();
					}

					case SELECT -> {
						SelectItem selectItem = (SelectItem)answer;
						Answer<List<Long>> a = selectItem.getAnswer();
						yield a != null && Objects.requireNonNull(a).answer() != null && !a.answer().isEmpty();
					}

					case CALENDAR -> {
						CalendarItem calendarItem = (CalendarItem)answer;
						Answer<LocalDate> a = calendarItem.getAnswer();
						yield a != null && Objects.requireNonNull(a).answer() != null;
					}

					case ANNOUNCEMENT -> true;
				};
			});
	}
}
