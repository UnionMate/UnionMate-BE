package com.unionmate.backend.domain.recruitment.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUpdateService {

	private final RecruitmentSaveService recruitmentSaveService;

	@Transactional
	public void changeActivation(Recruitment recruitment, boolean requestedActive, LocalDateTime now) {
		boolean before = Boolean.TRUE.equals(recruitment.getIsActive());
		recruitment.changeActivation(now, requestedActive);

		boolean after = Boolean.TRUE.equals(recruitment.getIsActive());
		if (after != before) {
			recruitmentSaveService.save(recruitment);
		}
	}
}
