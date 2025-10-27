package com.unionmate.backend.domain.recruitment.application.dto.response;

import com.unionmate.backend.domain.recruitment.application.exception.ItemTypeNotExistException;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

public sealed interface ItemResponse permits TextResponse, SelectResponse, CalendarResponse, AnnouncementResponse {
	Long id();

	ItemType type();

	boolean required();

	String title();

	Integer order();

	String description();

	static ItemResponse from(Item item) {
		ItemType itemType = item.getItemType();

		return switch (item) {
			case TextItem textItem -> TextResponse.from(textItem, itemType);
			case SelectItem selectItem -> SelectResponse.from(selectItem, itemType);
			case CalendarItem calendarItem -> CalendarResponse.from(calendarItem, itemType);
			case AnnouncementItem announcementItem -> AnnouncementResponse.from(announcementItem, itemType);
			default -> throw new ItemTypeNotExistException();
		};

	}
}
