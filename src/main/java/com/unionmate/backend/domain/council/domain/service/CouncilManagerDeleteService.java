package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.repository.CouncilManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouncilManagerDeleteService {
	private final CouncilManagerRepository councilManagerRepository;

	public void delete(CouncilManager councilManager) {
		councilManagerRepository.delete(councilManager);
	}
}