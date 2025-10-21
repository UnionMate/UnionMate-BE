package com.unionmate.backend.domain.applicant.application.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
	@JsonSubTypes.Type(value = TextAnswerRequest.class),
	@JsonSubTypes.Type(value = SelectAnswerRequest.class),
	@JsonSubTypes.Type(value = CalendarAnswerRequest.class)
})
public sealed interface AnswerRequest permits TextAnswerRequest, SelectAnswerRequest, CalendarAnswerRequest {
	@NotNull
	Long itemId();
}
