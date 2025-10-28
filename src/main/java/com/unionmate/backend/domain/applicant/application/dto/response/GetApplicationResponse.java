package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.Comparator;
import java.util.List;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetApplicationResponse(
	@Schema(description = "지원서 id", example = "1")
	Long applicationId,

	@Schema(description = "지원서 양식 id", example = "1")
	Long recruitmentId,

	@Schema(description = "지원서 이름", example = "25대 컴퓨터공학과 학생회 모집")
	String recruitmentName,

	@Schema(description = "지원자 이름", example = "김가천")
	String name,

	@Schema(description = "지원자 이메일", example = "unionmate@unionmate.com")
	String email,

	@Schema(description = "지원자 전화번호", example = "010-6658-6316")
	String tel,
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
			application.getName(),
			application.getEmail(),
			application.getTel(),
			answer
		);
	}
}
