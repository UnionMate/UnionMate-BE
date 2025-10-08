package com.unionmate.backend.domain.recruitment.application.dto.response;

public record SelectOptionResponse(
	Long id,
	String title,
	Integer order,
	Boolean isEtc,
	String etcTitle
) {
}
