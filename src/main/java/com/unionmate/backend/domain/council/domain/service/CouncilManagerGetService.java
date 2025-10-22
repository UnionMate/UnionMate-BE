package com.unionmate.backend.domain.council.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.Council;
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

	public List<CouncilManager> getAllCouncilMembers(Council council) {
		return councilManagerRepository.findAllByCouncil(council);
	}
}
