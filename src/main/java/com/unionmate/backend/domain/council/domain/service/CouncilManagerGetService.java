package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.repository.CouncilManagerRepository;
import com.unionmate.backend.domain.council.exception.CouncilManagerNotFoundException;
import com.unionmate.backend.domain.member.domain.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouncilManagerGetService {
	private final CouncilManagerRepository councilManagerRepository;

	public boolean existsByMember(Member member) {
		return councilManagerRepository.existsByMember(member);
	}

	public CouncilManager getCouncilManagerByMember(Member member) {
		return councilManagerRepository.findByMember(member)
			.orElseThrow(CouncilManagerNotFoundException::new);
	}

	public CouncilManager getCouncilManager(long councilManagerId) {
		return councilManagerRepository.findById(councilManagerId)
			.orElseThrow(CouncilManagerNotFoundException::new);
	}
}
