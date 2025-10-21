package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.repository.CouncilManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouncilManagerSaveService {
	private final CouncilManagerRepository councilManagerRepository;

	public CouncilManager save(CouncilManager manager) {
		return councilManagerRepository.save(manager);
	}
}
