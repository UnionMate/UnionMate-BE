package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
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

	//Single table 전략인데 하위 타입 전용 컬럼인 date를 nullable = false로 해서는 안됩니다.
	//items 테이블에 모든 하위 컬럼이 있고, SELECT 타입을 insert할 때 값이 없는데도 not null을 요구한다면 오류가 생깁니다.
	@Column(name = "date")
	private LocalDate date;

	@Convert(converter = LocalDateAnswerConverter.class)
	@Lob
	private Answer<LocalDate> answer;

	public void updateAnswer(Answer<LocalDate> answer) {
		this.answer = answer;
	}

	public static CalendarItem createApplicationCalendar(Application application, Boolean required, String title,
		Integer order, String description, LocalDate date, ItemType itemType) {
		return CalendarItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.date(date)
			.itemType(itemType)
			.build();
	}
}
