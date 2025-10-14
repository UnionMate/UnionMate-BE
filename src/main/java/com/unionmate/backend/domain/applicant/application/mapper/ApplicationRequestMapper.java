package com.unionmate.backend.domain.applicant.application.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

@Component

public class ApplicationRequestMapper {
	public Application toApplication(
		String name, String email, String tel, Recruitment recruitment
	) {
		return Application.builder()
			.name(name)
			.email(email)
			.tel(tel)
			.recruitment(recruitment)
			.build();
	}

	public TextItem toTextItem(
		Application application, Boolean required, String title, Integer order, String description, Integer maxLength,
		ItemType itemType
	) {
		return TextItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.maxLength(maxLength)
			.itemType(itemType)
			.build();
	}

	public SelectItem toSelectItem(
		Application application, Boolean required, String title, Integer order, String description, Boolean multiple,
		ItemType itemType
	) {
		return SelectItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.multiple(multiple)
			.itemType(itemType)
			.build();
	}

	public CalendarItem toCalendarItem(
		Application application, Boolean required, String title, Integer order, String description, LocalDate date,
		ItemType itemType
	) {
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
