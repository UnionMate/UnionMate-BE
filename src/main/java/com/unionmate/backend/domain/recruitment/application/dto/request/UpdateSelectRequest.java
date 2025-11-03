package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateSelectRequest(
	@NotNull
	Long id,
	Boolean required,
	String title,
	Integer order,
	String description,
	Boolean multiple,
	List<Long> removeOptions,
	List<@Valid UpdateSelectOptionRequest> updateOptions
) implements UpdateItemRequest {
}
