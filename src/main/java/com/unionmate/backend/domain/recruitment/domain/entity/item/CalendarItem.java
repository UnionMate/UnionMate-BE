package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.LocalDateAnswerConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue(DiscriminationValue.CALENDAR)
public class CalendarItem extends Item {

	@Convert(converter = LocalDateAnswerConverter.class)
	@Lob
	private Answer<LocalDate> answer;

	public void updateAnswer(Answer<LocalDate> answer) {
		this.answer = answer;
	}

	public static CalendarItem createApplicationCalendar(Application application, Boolean required, String title,
		Integer order, String description) {
		return CalendarItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.build();
	}

	public static CalendarItem createRecruitmentCalendar(Recruitment recruitment, Boolean required, String title,
		Integer order, String description) {
		return CalendarItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.build();
	}
}
