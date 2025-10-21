package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.repository.CouncilRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouncilSaveService {
	private final CouncilRepository councilRepository;

	public Council createCouncil(Council council) {
		return councilRepository.save(council);
	}
}
