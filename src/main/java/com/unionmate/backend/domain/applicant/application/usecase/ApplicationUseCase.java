package com.unionmate.backend.domain.applicant.application.usecase;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateApplicantRequest;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationSaveService;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUseCase {
	private final ApplicationSaveService applicationSaveService;
	private final RecruitmentGetService recruitmentGetService;

	@Transactional
	public void submitApplication(Long recruitmentId, CreateApplicantRequest createApplicantRequest) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		Map<Long, Item> templateById = recruitment.getItems()
			.stream().collect(Collectors.toMap(Item::getId, item -> item));
	}
}
