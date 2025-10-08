package com.unionmate.backend.domain.recruitment.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record AnnouncementResponse(
	Long id,
	ItemType type,
	boolean required,
	String title,
	Integer order,
	String description,
	String announcement
) implements ItemResponse {
}
