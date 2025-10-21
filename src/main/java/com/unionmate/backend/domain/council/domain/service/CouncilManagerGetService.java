package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.repository.CouncilManagerRepository;
import com.unionmate.backend.domain.member.domain.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouncilManagerGetService {
	private final CouncilManagerRepository councilManagerRepository;

	public boolean existsByMember(Member member) {
		return councilManagerRepository.existsByMember(member);
	}
}
