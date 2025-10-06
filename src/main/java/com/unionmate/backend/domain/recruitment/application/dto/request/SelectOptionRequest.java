package com.unionmate.backend.domain.recruitment.application.dto.request;

public record SelectOptionRequest(
	String title,
	Integer order,
	Boolean isEtc,
	String ectTitle
) {
}
