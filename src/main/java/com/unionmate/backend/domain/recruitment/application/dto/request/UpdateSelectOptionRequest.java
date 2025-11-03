package com.unionmate.backend.domain.recruitment.application.dto.request;

public record UpdateSelectOptionRequest(
	Long id,
	String title,
	Integer order,
	Boolean isEtc,
	String etcTitle
) {
}
