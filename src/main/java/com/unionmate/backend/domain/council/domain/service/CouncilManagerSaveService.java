package com.unionmate.backend.domain.council.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.entity.enums.CouncilRole;
import com.unionmate.backend.domain.council.domain.repository.CouncilManagerRepository;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.entity.School;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CouncilManagerSaveService {
	private final CouncilManagerRepository councilManagerRepository;

	public CouncilManager save(CouncilManager manager) {
		return councilManagerRepository.save(manager);
	}
}