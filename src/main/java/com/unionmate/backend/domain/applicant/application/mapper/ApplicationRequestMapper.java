package com.unionmate.backend.domain.applicant.application.mapper;

import org.springframework.stereotype.Component;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

@Component

public class ApplicationRequestMapper {
	public Application toApplication(
		String name, String email, String tel, Recruitment recruitment
	) {
		return Application.builder()
			.name(name)
			.email(email)
			.tel(tel)
			.recruitment(recruitment)
			.build();
	}
}
