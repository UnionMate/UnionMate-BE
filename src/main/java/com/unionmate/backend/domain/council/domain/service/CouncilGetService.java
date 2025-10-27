package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.repository.CouncilRepository;
import com.unionmate.backend.domain.council.exception.CouncilNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouncilGetService {
	private final CouncilRepository councilRepository;

	public Council getCouncilById(String invitationCode) {
		return councilRepository.findByInvitationCode(invitationCode)
			.orElseThrow(CouncilNotFoundException::new);
	}

	public Council getCouncilById(long councilId) {
		return councilRepository.findById(councilId)
			.orElseThrow(CouncilNotFoundException::new);
	}
}
