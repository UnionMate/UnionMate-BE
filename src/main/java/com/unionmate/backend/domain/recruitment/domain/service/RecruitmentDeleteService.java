package com.unionmate.backend.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentDeleteService {
	private final RecruitmentRepository recruitmentRepository;

	public void deleteRecruitment(Long id) {
		recruitmentRepository.deleteById(id);
	}
}
