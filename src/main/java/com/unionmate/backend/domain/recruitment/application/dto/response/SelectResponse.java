package com.unionmate.backend.domain.recruitment.application.dto.response;

import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

public record SelectResponse(
	Long id,
	ItemType type,
	boolean required,
	String title,
	Integer order,
	String description,
	boolean multiple,
	List<SelectOptionResponse> options
) implements ItemResponse {
}
