package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.Comparator;
import java.util.List;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

public record GetApplicationResponse(
	Long applicationId,
	Long recruitmentId,
	String recruitmentName,
	List<ApplicationAnswerResponse> answers
) {
	public static GetApplicationResponse from(Application application) {
		List<ApplicationAnswerResponse> answer = application.getAnswers().stream()
			.sorted(Comparator.comparing(Item::getOrder))
			.map(ApplicationAnswerResponse::from)
			.toList();

		return new GetApplicationResponse(
			application.getId(),
			application.getRecruitment().getId(),
			application.getRecruitment().getName(),
			answer
		);
	}
}
