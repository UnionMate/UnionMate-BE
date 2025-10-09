package com.unionmate.backend.domain.recruitment.application.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.recruitment.application.dto.response.AnnouncementResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.CalendarResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.ItemResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.SelectOptionResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.SelectResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.TextResponse;
import com.unionmate.backend.domain.recruitment.application.exception.ItemTypeNotExistException;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

@Component
public class RecruitmentGetMapper {
	public RecruitmentResponse toRecruitmentResponse(Recruitment recruitment) {
		List<ItemResponse> items = recruitment.getItems().stream()
			.map(this::toItemResponse)
			.toList();

		return new RecruitmentResponse(
			recruitment.getId(),
			recruitment.getName(),
			recruitment.getStartAt(),
			recruitment.getEndAt(),
			recruitment.getIsActive(),
			recruitment.getRecruitmentStatus(),
			items
		);
	}

	private ItemResponse toItemResponse(Item item) {
		ItemType itemType = item.getItemType();

		if (item instanceof TextItem ti) {
			return new TextResponse(
				ti.getId(),
				itemType,
				ti.getRequired(),
				ti.getTitle(),
				ti.getOrder(),
				ti.getDescription(),
				ti.getText()
			);
		}

		if (item instanceof SelectItem selectItem) {
			List<SelectOptionResponse> options = selectItem.getSelectItemOptions().stream()
				.map(
					o -> new SelectOptionResponse(
						o.getId(),
						o.getTitle(),
						o.getOrder(),
						o.getIsEtc(),
						o.getEtcTitle()
					)
				)
				.toList();

			return new SelectResponse(
				selectItem.getId(),
				itemType,
				selectItem.getRequired(),
				selectItem.getTitle(),
				selectItem.getOrder(),
				selectItem.getDescription(),
				selectItem.isMultiple(),
				options
			);
		}

		if (item instanceof CalendarItem calendarItem) {
			return new CalendarResponse(
				calendarItem.getId(),
				itemType,
				calendarItem.getRequired(),
				calendarItem.getTitle(),
				calendarItem.getOrder(),
				calendarItem.getDescription(),
				calendarItem.getDate()
			);
		}

		if (item instanceof AnnouncementItem announcementItem) {
			return new AnnouncementResponse(
				announcementItem.getId(),
				itemType,
				announcementItem.getRequired(),
				announcementItem.getTitle(),
				announcementItem.getOrder(),
				announcementItem.getDescription(),
				announcementItem.getAnnouncement()
			);
		}
		throw new ItemTypeNotExistException();
	}
}
