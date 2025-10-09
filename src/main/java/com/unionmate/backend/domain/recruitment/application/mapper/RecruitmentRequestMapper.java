package com.unionmate.backend.domain.recruitment.application.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

@Component
public class RecruitmentRequestMapper {
	public Recruitment toRecruitment(
		String name, LocalDateTime startAt, LocalDateTime endAt, Boolean isActive, RecruitmentStatus recruitmentStatus
	) {
		return Recruitment.builder()
			.name(name)
			.startAt(startAt)
			.endAt(endAt)
			.isActive(isActive)
			.recruitmentStatus(recruitmentStatus)
			.build();
	}

	public Item toItem(Recruitment recruitment, CreateItemRequest ir) {
		boolean required = Boolean.TRUE.equals(ir.required());
		boolean multiple = Boolean.TRUE.equals(ir.multiple());

		return switch (ir.type()) {
			case TEXT ->
				toTextItem(recruitment, required, ir.title(), ir.order(), ir.description(), ir.text(), ir.maxLength());

			case SELECT -> {
				SelectItem selectItem = toSelectItem(
					recruitment, required, ir.title(), ir.order(), ir.description(), multiple);

				if (ir.options() != null) {
					for (SelectOptionRequest or : ir.options()) {
						selectItem.getSelectItemOptions().add(
							toSelectItemOption(or.title(), or.order(), Boolean.TRUE.equals(or.isEtc()), or.etcTitle(), selectItem));
					}
				}
				yield selectItem;
			}

			case CALENDAR ->
				toCalendarItem(recruitment, required, ir.title(), ir.order(), ir.description(), ir.date());

			case ANNOUNCEMENT ->
				toAnnouncementItem(recruitment, required, ir.title(), ir.order(), ir.description(), ir.announcement());
		};
	}

	private TextItem toTextItem(
		Recruitment recruitment, Boolean required, String title, Integer order, String description, String text, Integer maxLength
	) {
		return TextItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.text(text)
			.maxLength(maxLength)
			.build();
	}

	private SelectItem toSelectItem(
		Recruitment recruitment, Boolean required, String title, Integer order, String description, Boolean multiple
	) {
		return SelectItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.multiple(multiple)
			.build();
	}

	private SelectItemOption toSelectItemOption(
		String title, Integer order, Boolean isEtc, String etcTitle, SelectItem selectItem
	) {
		return SelectItemOption.builder()
			.title(title)
			.order(order)
			.isEtc(isEtc)
			.etcTitle(etcTitle)
			.selectItem(selectItem)
			.build();
	}

	private CalendarItem toCalendarItem(
		Recruitment recruitment, Boolean required, String title, Integer order, String description, LocalDate date
	) {
		return CalendarItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.date(date)
			.build();
	}

	private AnnouncementItem toAnnouncementItem(
		Recruitment recruitment, Boolean required, String title, Integer order, String description, String announcement
	) {
		return AnnouncementItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.announcement(announcement)
			.build();
	}
}
