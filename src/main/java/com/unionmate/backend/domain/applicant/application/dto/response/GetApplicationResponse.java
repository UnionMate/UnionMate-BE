package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.List;

public record GetApplicationResponse(
	Long applicationId,
	Long recruitmentId,
	String recruitmentName,
	List<ApplicationAnswerResponse> answers
) {

}
