package com.unionmate.backend.domain.recruitment.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.response.GetRecruitmentsResponse;
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

	public List<GetRecruitmentsResponse> getRecruitments(Long councilId) {
		return recruitmentRepository.findAllByCouncilIdOrderByCreatedAtDesc(councilId).stream()
			.map(GetRecruitmentsResponse::from)
			.toList();
	}
}
