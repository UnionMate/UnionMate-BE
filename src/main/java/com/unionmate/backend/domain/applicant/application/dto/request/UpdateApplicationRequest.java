package com.unionmate.backend.domain.applicant.application.dto.request;

import java.util.List;

public record UpdateApplicationRequest(
	String name,
	String email,
	String tel,
	List<AnswerRequest> answers
) {
}
