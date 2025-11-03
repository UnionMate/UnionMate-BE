package com.unionmate.backend.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUpdateService {
	public void updateRecruitment(Recruitment recruitment, CreateRecruitmentRequest createRecruitmentRequest) {
		if (createRecruitmentRequest.name() != null) {
			recruitment.updateName(createRecruitmentRequest.name());
		}

		if (createRecruitmentRequest.endAt() != null) {
			recruitment.updateEndAt(createRecruitmentRequest.endAt());
		}

		if (createRecruitmentRequest.isActive() != null) {
			recruitment.updateIsActive(createRecruitmentRequest.isActive());
		}

		if (createRecruitmentRequest.recruitmentStatus() != null) {
			recruitment.updateStatus(createRecruitmentRequest.recruitmentStatus());
		}
	}
}
