package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record CreateItemRequest(
	ItemType type,
	Boolean required,
	String title,
	Integer order,
	String description,

	//Select용
	Boolean multiple,
	List<SelectOptionRequest> options,

	//text용
	String text,

	//calendar용
	LocalDate date,

	//announcement용
	String announcement
) {
}
