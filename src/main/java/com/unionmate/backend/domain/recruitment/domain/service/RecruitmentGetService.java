package com.unionmate.backend.domain.recruitment.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.exception.RecruitmentNotFoundException;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {
	private final RecruitmentRepository recruitmentRepository;

	public Recruitment getRecruitmentById(Long id) {
		return recruitmentRepository.findFormById(id)
			.orElseThrow(RecruitmentNotFoundException::new);
	}
}
