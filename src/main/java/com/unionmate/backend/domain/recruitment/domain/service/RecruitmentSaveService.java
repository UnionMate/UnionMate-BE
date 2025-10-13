package com.unionmate.backend.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentSaveService {
	private final RecruitmentRepository recruitmentRepository;

	public void save(Recruitment recruitment) {
		recruitmentRepository.save(recruitment);
	}
}
