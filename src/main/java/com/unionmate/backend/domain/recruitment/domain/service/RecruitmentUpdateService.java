package com.unionmate.backend.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUpdateService {
	public void updateRecruitment(Recruitment recruitment, UpdateRecruitmentRequest updateRecruitmentRequest) {
		if (updateRecruitmentRequest.name() != null) {
			recruitment.updateName(updateRecruitmentRequest.name());
		}

		if (updateRecruitmentRequest.endAt() != null) {
			recruitment.updateEndAt(updateRecruitmentRequest.endAt());
		}

		if (updateRecruitmentRequest.isActive() != null) {
			recruitment.updateIsActive(updateRecruitmentRequest.isActive());
		}

		if (updateRecruitmentRequest.recruitmentStatus() != null) {
			recruitment.updateStatus(updateRecruitmentRequest.recruitmentStatus());
		}
	}
}
